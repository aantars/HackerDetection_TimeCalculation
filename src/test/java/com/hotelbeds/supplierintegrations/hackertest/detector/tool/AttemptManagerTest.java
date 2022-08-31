package com.hotelbeds.supplierintegrations.hackertest.detector.tool;

import com.hotelbeds.supplierintegrations.hackertest.detector.model.FailureAttempt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AttemptManagerTest {

    @Autowired
    private AttemptsManager attemptsManager;

    @BeforeEach
    void setup() {
       ((AttemptsManagerImpl)attemptsManager).init();
    }

    @Test
    public void checkIsAttackCandidate_with_0_previous_attempts_test(){
        FailureAttempt dummyFailureAttempt = generateDummyFailureAttempt();

        boolean isCandidate = attemptsManager.isAttackCandidate(dummyFailureAttempt);

        assertFalse(isCandidate);
    }

    @Test
    public void checkIsAttackCandidate_with_4_attempts_test(){
        FailureAttempt dummyFailureAttempt = generateDummyFailureAttempt();

        attemptsManager.isAttackCandidate(dummyFailureAttempt);
        attemptsManager.isAttackCandidate(dummyFailureAttempt);
        attemptsManager.isAttackCandidate(dummyFailureAttempt);
        boolean isCandidate = attemptsManager.isAttackCandidate(dummyFailureAttempt);

        assertFalse(isCandidate);
    }

    @Test
    public void checkIsAttackCandidate_with_5_attempts_test(){
        FailureAttempt dummyFailureAttempt = new FailureAttempt("127.0.0.1", LocalDateTime.now(), "Will.Smith",1);

        attemptsManager.isAttackCandidate(dummyFailureAttempt);
        attemptsManager.isAttackCandidate(dummyFailureAttempt);
        attemptsManager.isAttackCandidate(dummyFailureAttempt);
        attemptsManager.isAttackCandidate(dummyFailureAttempt);
        boolean isCandidate = attemptsManager.isAttackCandidate(dummyFailureAttempt);

        assertTrue(isCandidate);
    }

    @Test
    public void removeDeprecatedItemsTest(){
        LocalDateTime dummyDateFromEpochTime = Instant.ofEpochMilli(133612947).atZone(ZoneId.systemDefault()).toLocalDateTime();
        FailureAttempt failureAttempt1 = new FailureAttempt("127.0.0.1", dummyDateFromEpochTime, "Will.Smith",1);
        FailureAttempt failureAttempt2 = new FailureAttempt("127.0.0.2", dummyDateFromEpochTime, "Will.Smith",1);
        FailureAttempt failureAttempt3 = new FailureAttempt("127.0.0.3", dummyDateFromEpochTime, "Will.Smith",1);
        attemptsManager.isAttackCandidate(failureAttempt1);
        attemptsManager.isAttackCandidate(failureAttempt2);
        attemptsManager.isAttackCandidate(failureAttempt3);

        boolean erased = attemptsManager.removeDeprecatedAttempts();

        assertTrue(erased);
    }

    private FailureAttempt generateDummyFailureAttempt() {
        LocalDateTime dummyDateFromEpochTime = Instant.ofEpochMilli(133612947).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return new FailureAttempt("127.0.0.1", dummyDateFromEpochTime, "Will.Smith", 1);
    }
}
