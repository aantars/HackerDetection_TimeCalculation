package com.hotelbeds.supplierintegrations.hackertest.detector.tool;

import com.hotelbeds.supplierintegrations.hackertest.detector.exception.EmptyLogLineException;
import com.hotelbeds.supplierintegrations.hackertest.detector.exception.TimeStampWrongFormatException;
import com.hotelbeds.supplierintegrations.hackertest.detector.exception.WrongLogLineFormatException;
import com.hotelbeds.supplierintegrations.hackertest.detector.model.FailureAttempt;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LineStructureProcessorTest {

    @MockBean
    LineStructureProcessorImpl lineStructureProcessor;

    private LineStructureProcessorImpl lineStructureProcessor1 = new LineStructureProcessorImpl();

    @Test
    public void validateLineAndReturnAsObjectTest(){
        String dummyLogLine = "80.238.9.179,133612947,SIGNIN_FAILURE,Will.Smith";
        LocalDateTime dummyDateFromEpochTime = Instant.ofEpochMilli(133612947).atZone(ZoneId.systemDefault()).toLocalDateTime();
        String ipAddress = "80.238.9.179";
        String UserName = "Will.Smith";
        FailureAttempt failureAttempt = new FailureAttempt(ipAddress, dummyDateFromEpochTime, UserName, 1);
        Optional<FailureAttempt> optionalFailureAttempt = Optional.of(failureAttempt);

        when(lineStructureProcessor.validateLineAndReturnAsObject(Mockito.anyString())).thenReturn(optionalFailureAttempt);

        Optional<FailureAttempt> failureAfterProcessing = lineStructureProcessor.validateLineAndReturnAsObject(dummyLogLine);

        assertEquals(failureAfterProcessing.get().getIpAddress(), "80.238.9.179");
        assertEquals(failureAfterProcessing.get().getTriggeredTimeStamp(), dummyDateFromEpochTime);
        assertEquals(failureAfterProcessing.get().getUserName(), "Will.Smith");
    }

    @Test
    public void validateLineAndReturnAsObject_SIGNIN_FAILURE_test(){
        String dummyFailureLogginLine = "80.238.9.179,133612947,SIGNIN_FAILURE,Will.Smith";

        Optional<FailureAttempt> failureAfterProcessing = lineStructureProcessor1.validateLineAndReturnAsObject(dummyFailureLogginLine);

        assertTrue(failureAfterProcessing.isPresent());
    }

    @Test
    public void validateLineAndReturnAsObject_SIGNIN_SUCCESS_test(){
        String dummySuccessLoginLine = "80.238.9.179,133612947,SIGNIN_SUCCESS,Will.Smith";

        Optional<FailureAttempt> failureAfterProcessing = lineStructureProcessor1.validateLineAndReturnAsObject(dummySuccessLoginLine);

        assertFalse(failureAfterProcessing.isPresent());
    }

    @Test
    public void validateLineAndReturnAsObject_emptyLineException_test(){
        String dummyEmptyLogLine = "";
        assertThrows(EmptyLogLineException.class, () -> lineStructureProcessor1.validateLineAndReturnAsObject(dummyEmptyLogLine));
    }

    @Test
    public void validateLineAndReturnAsObject_malformedLineException_test(){
        String dummymalformedLoginLine = "133612947,SIGNIN_SUCCESS,Will.Smith";
        assertThrows(WrongLogLineFormatException.class, () -> lineStructureProcessor1.validateLineAndReturnAsObject(dummymalformedLoginLine));
    }

    @Test
    public void validateLineAndReturnAsObject_timeStampWrongFormatException_test(){
        String dummyWrongTimeStampLogginLine = "80.238.9.179,dateTime,SIGNIN_FAILURE,Will.Smith";
        assertThrows(TimeStampWrongFormatException.class, () -> lineStructureProcessor1.validateLineAndReturnAsObject(dummyWrongTimeStampLogginLine));
    }

}