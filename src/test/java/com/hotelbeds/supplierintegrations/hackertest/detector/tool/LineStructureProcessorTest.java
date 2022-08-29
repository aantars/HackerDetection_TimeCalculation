package com.hotelbeds.supplierintegrations.hackertest.detector.tool;

import com.hotelbeds.supplierintegrations.hackertest.detector.model.FailureAttempt;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LineStructureProcessorTest {

    @MockBean
    LineStructureProcessorImpl lineStructureProcessor;

    @Test
    public void validateLineAndReturnAsObjectTest(){
        String dummyLogLine = "80.238.9.179,133612947,SIGNIN_FAILURE,Will.Smith";
        LocalDateTime dummyDateFromEpochTime = Instant.ofEpochMilli(133612947).atZone(ZoneId.systemDefault()).toLocalDateTime();
        String ipAddress = "80.238.9.179";
        String UserName = "Will.Smith";
        FailureAttempt failureAttempt = new FailureAttempt(ipAddress, dummyDateFromEpochTime, UserName);
        Optional<FailureAttempt> optionalFailureAttempt = Optional.of(failureAttempt);

        when(lineStructureProcessor.validateLineAndReturnAsObject(Mockito.anyString())).thenReturn(optionalFailureAttempt);

        Optional<FailureAttempt> failureAfterProcessing = lineStructureProcessor.validateLineAndReturnAsObject(dummyLogLine);

        assertEquals(failureAfterProcessing.get().getIpAddress(), "80.238.9.179");
        assertEquals(failureAfterProcessing.get().getTriggeredTimeStamp(), dummyDateFromEpochTime);
        assertEquals(failureAfterProcessing.get().getUserName(), "Will.Smith");
    }

    @Test
    public void validateLineAndReturnAsObjectTestNotMocked(){
        String dummyLogLine = "80.238.9.179,133612947,SIGNIN_FAILURE,Will.Smith";
        LocalDateTime dummyDateFromEpochTime = Instant.ofEpochMilli(133612947).atZone(ZoneId.systemDefault()).toLocalDateTime();
        String ipAddress = "80.238.9.179";
        String UserName = "Will.Smith";
        FailureAttempt failureAttempt = new FailureAttempt(ipAddress, dummyDateFromEpochTime, UserName);

        // when(lineStructureProcessor.validateLineAndReturnAsObject(Mockito.anyString())).thenReturn(failureAttempt);

        Optional<FailureAttempt> failureAfterProcessing = lineStructureProcessor.validateLineAndReturnAsObject(dummyLogLine);

        assertTrue(failureAfterProcessing.isPresent());
        /*assertEquals(failureAfterProcessing.getIpAddress(), "80.238.9.179");
        assertEquals(failureAfterProcessing.getTriggeredDate(), dummyDateFromEpochTime);
        assertEquals(failureAttempt.getUserName(), "Will.Smith");*/
    }

}