package com.hotelbeds.supplierintegrations.hackertest.detector.tool;

import com.hotelbeds.supplierintegrations.hackertest.detector.HackerDetectorImpl;
import com.hotelbeds.supplierintegrations.hackertest.detector.exception.UnableToSaveFailureException;
import com.hotelbeds.supplierintegrations.hackertest.detector.exception.WrongLogLineFormatException;
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

    @Test
    public void saveFailureAttemptTest(){
        String dummyLogLine = "80.238.9.179,133612947,SIGNIN_FAILURE,Will.Smith";
        LocalDateTime dummyDateFromEpochTime = Instant.ofEpochMilli(133612947).atZone(ZoneId.systemDefault()).toLocalDateTime();
        FailureAttempt failureAttempt = new FailureAttempt("127.0.0.1", dummyDateFromEpochTime, "Will.Smith",1);

       // attemptsManager.collectionInitialSetUp();
        Map<String, FailureAttempt> loginAttemptsCollection = new ConcurrentHashMap<>();
        Optional<FailureAttempt> savedFailureAttempt = attemptsManager.saveFailureAttempt("127.0.0.1", failureAttempt);

        assertTrue(savedFailureAttempt.isPresent());
    }

    @Test
    @Disabled
    public void unableToSaveFailureExceptionTest(){
        // TODO: now is used in save, but need to be testes through attemptsManager.saveFailureAttempt
        String dummyLogLine = "80.238.9.179,133612947,SIGNIN_FAILURE,Will.Smith";
        LocalDateTime dummyDateFromEpochTime = Instant.ofEpochMilli(133612947).atZone(ZoneId.systemDefault()).toLocalDateTime();

        FailureAttempt failureAttempt = new FailureAttempt("127.0.0.1", dummyDateFromEpochTime, "Will.Smith",1);

        assertThrows(UnableToSaveFailureException.class, () -> attemptsManager.saveFailureAttempt("127.0.0.1", failureAttempt));

    }
    // check previous attempts for this IP - (last 5')?
    @Test
    @Disabled
    public void checkPreviousAttemps_0_previous_attempt_Test(){
        // save example in list
        String dummyLogLine = "80.238.9.179,133612947,SIGNIN_FAILURE,Will.Smith";
        LocalDateTime dummyDateFromEpochTime = Instant.ofEpochMilli(133612947).atZone(ZoneId.systemDefault()).toLocalDateTime();

        FailureAttempt failureAttempt = new FailureAttempt("127.0.0.1", dummyDateFromEpochTime, "Will.Smith",1);

       // attemptsManager.saveFailureAttempt("127.0.0.1", failureAttempt);
        Map<String, FailureAttempt> loginAttemptsCollection = new ConcurrentHashMap<>();
        loginAttemptsCollection.put("127.0.0.1", failureAttempt);

        int previousAttempts = attemptsManager.checkPreviousAttempsByIP(failureAttempt.getIpAddress());

        assertEquals(0,failureAttempt.getAttemptsAmount());

    }

    // delete all older than 5'
    // if no previous found --> insert new
    // if previous found && < 4 --> update
    // if previous found && >= 4 --> return IP and delete

}
