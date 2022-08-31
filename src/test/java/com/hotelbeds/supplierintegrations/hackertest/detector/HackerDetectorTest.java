package com.hotelbeds.supplierintegrations.hackertest.detector;

import com.hotelbeds.supplierintegrations.hackertest.detector.exception.NullLogLineException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class HackerDetectorTest {

    @Autowired
    private HackerDetector hackerDetector;

    @BeforeEach
    void setup() {
        ((HackerDetectorImpl)hackerDetector).init();
    }

    @Test
    public void parseLineTest_For_Single_FailureSignin(){
        String dummyLogLineFailureLogin = "80.238.9.179,"+getCurrentTimeInEpochTime()+",SIGNIN_FAILURE,Will.Smith";

        String result = hackerDetector.parseLine(dummyLogLineFailureLogin);

        assertNull(result);
    }

    @Test
    public void parseLineTest_For_SuccessSignin(){
        String dummyLogLineSuccessLogin = "80.238.9.179,133612947,SIGNIN_SUCCESS,Will.Smith";

        String result = hackerDetector.parseLine(dummyLogLineSuccessLogin);

        assertNull(result);
    }

    @Test
    public void parseLineTest_For_4_FailureSignin(){
        String dummyLogLineFailureLogin = "80.238.9.179,"+getCurrentTimeInEpochTime()+",SIGNIN_FAILURE,Will.Smith";

        hackerDetector.parseLine(dummyLogLineFailureLogin);
        hackerDetector.parseLine(dummyLogLineFailureLogin);
        hackerDetector.parseLine(dummyLogLineFailureLogin);
        String result = hackerDetector.parseLine(dummyLogLineFailureLogin);

        assertNull(result);
    }

    @Test
    public void parseLineTest_For_5_FailureSignin(){
        String dummyLogLineFailureLogin = "80.238.9.179,"+getCurrentTimeInEpochTime()+",SIGNIN_FAILURE,Will.Smith";
        String dummyLogLineFailureLogin2 = "80.238.9.180,"+getCurrentTimeInEpochTime()+",SIGNIN_FAILURE,Will.Smith";

        hackerDetector.parseLine(dummyLogLineFailureLogin);
        hackerDetector.parseLine(dummyLogLineFailureLogin2);
        hackerDetector.parseLine(dummyLogLineFailureLogin);
        hackerDetector.parseLine(dummyLogLineFailureLogin);
        hackerDetector.parseLine(dummyLogLineFailureLogin2);
        hackerDetector.parseLine(dummyLogLineFailureLogin);
        String result = hackerDetector.parseLine(dummyLogLineFailureLogin);

        assertNotNull(result);
        assertEquals("80.238.9.179",result);
    }

    @Test
    public void nullLogLineExceptionTest(){
        assertThrows(NullLogLineException.class, () -> hackerDetector.parseLine(null));
    }

    private String getCurrentTimeInEpochTime() {
        Instant deadLineInstant = LocalDateTime.now().atZone(ZoneId.systemDefault()).
                toInstant();
        long epochFormatDeadLineTime = deadLineInstant.getEpochSecond();
        return Long.toString(epochFormatDeadLineTime);
    }
}
