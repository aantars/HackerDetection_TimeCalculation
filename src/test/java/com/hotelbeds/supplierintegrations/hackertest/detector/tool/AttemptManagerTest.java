package com.hotelbeds.supplierintegrations.hackertest.detector.tool;

import com.hotelbeds.supplierintegrations.hackertest.detector.model.FailureAttempt;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AttemptManagerTest {

    private AttemptsManagerImpl attemptsManager = new AttemptsManagerImpl();

    // check previous attempts for this IP - (last 5')?
    @Test
    public void checkPreviousAttemps_1_previous_attempt_Test(){
        // save example in list
        String dummyLogLine = "80.238.9.179,133612947,SIGNIN_FAILURE,Will.Smith";
        LocalDateTime dummyDateFromEpochTime = Instant.ofEpochMilli(133612947).atZone(ZoneId.systemDefault()).toLocalDateTime();

        FailureAttempt failureAttempt = new FailureAttempt("127.0.0.1", dummyDateFromEpochTime, "Will.Smith",1);

        Map<String, FailureAttempt> loginAttemptsCollection = new ConcurrentHashMap<>();
        loginAttemptsCollection.put("127.0.0.1", failureAttempt);

        int previousAttempts = attemptsManager.checkPreviousAttempsByIP(failureAttempt.getIpAddress());

        assertEquals(1,failureAttempt.getAttemptsAmount());

    }

    // delete all older than 5'
    // if no previous found --> insert new
    // if previous found && < 4 --> update
    // if previous found && >= 4 --> return IP and delete

}
