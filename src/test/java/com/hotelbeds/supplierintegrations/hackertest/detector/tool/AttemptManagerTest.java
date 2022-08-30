package com.hotelbeds.supplierintegrations.hackertest.detector.tool;

import com.hotelbeds.supplierintegrations.hackertest.detector.exception.UpdateListException;
import com.hotelbeds.supplierintegrations.hackertest.detector.model.FailureAttempt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AttemptManagerTest {

   // private AttemptsManagerImpl attemptsManager = new AttemptsManagerImpl();

    @Autowired
    private AttemptsManager attemptsManager;

    @BeforeEach
    void setup() {
       ((AttemptsManagerImpl)attemptsManager).init();
    }

    // check previous attempts for this IP - (last 5')?
    @Test
    public void checkPreviousAttemps_0_previous_attempt_Test(){
        // save example in list
        String dummyLogLine = "80.238.9.179,133612947,SIGNIN_FAILURE,Will.Smith";
        LocalDateTime dummyDateFromEpochTime = Instant.ofEpochMilli(133612947).atZone(ZoneId.systemDefault()).toLocalDateTime();

        FailureAttempt failureAttempt = new FailureAttempt("127.0.0.1", dummyDateFromEpochTime, "Will.Smith",1);

        boolean previousAttempts = attemptsManager.isAttackCandidate(failureAttempt);

        assertFalse(previousAttempts);
    }
    @Test
    public void checkPreviousAttemps_4_attempts_Test(){
        // save example in list
        String dummyLogLine = "80.238.9.179,133612947,SIGNIN_FAILURE,Will.Smith";
        LocalDateTime dummyDateFromEpochTime = Instant.ofEpochMilli(133612947).atZone(ZoneId.systemDefault()).toLocalDateTime();

        FailureAttempt failureAttempt = new FailureAttempt("127.0.0.1", dummyDateFromEpochTime, "Will.Smith",1);

        boolean previousAttempts1 = attemptsManager.isAttackCandidate(failureAttempt);
        boolean previousAttempts2 = attemptsManager.isAttackCandidate(failureAttempt);
        boolean previousAttempts3 = attemptsManager.isAttackCandidate(failureAttempt);
        boolean previousAttempts4 = attemptsManager.isAttackCandidate(failureAttempt);

        assertFalse(previousAttempts4);
    }

    @Test
    public void checkPreviousAttemps_5_attempts_Test(){
        // save example in list
/*
        String dummyLogLine = "80.238.9.179,133612947,SIGNIN_FAILURE,Will.Smith";
        LocalDateTime dummyDateFromEpochTime = Instant.ofEpochMilli(133612947).atZone(ZoneId.systemDefault()).toLocalDateTime();
*/

        FailureAttempt failureAttempt = new FailureAttempt("127.0.0.1", LocalDateTime.now(), "Will.Smith",1);

        boolean previousAttempts1 = attemptsManager.isAttackCandidate(failureAttempt);
        boolean previousAttempts2 = attemptsManager.isAttackCandidate(failureAttempt);
        boolean previousAttempts3 = attemptsManager.isAttackCandidate(failureAttempt);
        boolean previousAttempts4 = attemptsManager.isAttackCandidate(failureAttempt);
        boolean previousAttempts5 = attemptsManager.isAttackCandidate(failureAttempt);

        assertTrue(previousAttempts5);
    }

    @Test
    public void removeDeprecatedItemsTest(){
        LocalDateTime dummyDateFromEpochTime = Instant.ofEpochMilli(133612947).atZone(ZoneId.systemDefault()).toLocalDateTime();
        FailureAttempt failureAttempt1 = new FailureAttempt("127.0.0.1", dummyDateFromEpochTime, "Will.Smith",1);
        FailureAttempt failureAttempt2 = new FailureAttempt("127.0.0.2", dummyDateFromEpochTime, "Will.Smith",1);
        FailureAttempt failureAttempt3 = new FailureAttempt("127.0.0.3", dummyDateFromEpochTime, "Will.Smith",1);
      /*  FailureAttempt failureAttemptTimeNow4 = new FailureAttempt("127.0.0.4", LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime(), "Will.Smith",1);
        FailureAttempt failureAttemptTimeNow5 = new FailureAttempt("127.0.0.5", LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime(), "Will.Smith",1);
        FailureAttempt failureAttemptTimeNow6 = new FailureAttempt("127.0.0.6", LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime(), "Will.Smith",1);
*/

        boolean previousAttempts1 = attemptsManager.isAttackCandidate(failureAttempt1);
        boolean previousAttempts2 = attemptsManager.isAttackCandidate(failureAttempt2);
        boolean previousAttempts3 = attemptsManager.isAttackCandidate(failureAttempt3);
   /*     boolean previousAttempts4 = attemptsManager.isAttackCandidate(failureAttemptTimeNow4);
        boolean previousAttempts5 = attemptsManager.isAttackCandidate(failureAttemptTimeNow5);
        boolean previousAttempts6 = attemptsManager.isAttackCandidate(failureAttemptTimeNow6);*/

        boolean erased = attemptsManager.removeDeprecatedAttempts();

        assertTrue(erased);
    }

    // delete all older than 5'
    // if no previous found --> insert new
    // if previous found && < 4 --> update
    // if previous found && >= 4 --> return IP and delete




    @Test // TODO: This test will be deleted because saveFailureAttempt must be private
    @Disabled
    public void justSaveFailureAttemptTest(){
        LocalDateTime dummyDateFromEpochTime = Instant.ofEpochMilli(133612947).atZone(ZoneId.systemDefault()).toLocalDateTime();
        FailureAttempt failureAttempt = new FailureAttempt("127.0.0.1", dummyDateFromEpochTime, "Will.Smith",1);

       // Optional<FailureAttempt> savedFailureAttempt = attemptsManager.saveFailureAttempt("127.0.0.1", failureAttempt);

       // assertTrue(savedFailureAttempt.isPresent());
    }

    @Test
    @Disabled
    public void unableToSaveFailureExceptionTest(){
        // TODO: now is used in save, but need to be testes through attemptsManager.saveFailureAttempt
        String dummyLogLine = "80.238.9.179,133612947,SIGNIN_FAILURE,Will.Smith";
        LocalDateTime dummyDateFromEpochTime = Instant.ofEpochMilli(133612947).atZone(ZoneId.systemDefault()).toLocalDateTime();

        FailureAttempt failureAttempt = new FailureAttempt("127.0.0.1", dummyDateFromEpochTime, "Will.Smith",1);

      //  assertThrows(UnableToSaveFailureException.class, () -> attemptsManager.saveFailureAttempt("127.0.0.1", failureAttempt));

    }
}
