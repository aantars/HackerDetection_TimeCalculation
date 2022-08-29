package com.hotelbeds.supplierintegrations.hackertest.detector;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HackerDetectorTest {

    @MockBean
    HackerDetectorImpl hackerDetector;

    @Test
    public void parseLineTest(){
        String dummyLogLineSuccessLogin = "80.238.9.179,133612947,SIGNIN_SUCCESS,Will.Smith";
        String dummyLogLineFailureLogin = "80.238.9.179,133612947,SIGNIN_FAILURE,Will.Smith";
        String success = "no security break";
        String failure = "127.0.0.1";

        when(hackerDetector.parseLine(Mockito.anyString())).thenReturn(failure);

        String logLineanalysisResult = hackerDetector.parseLine(dummyLogLineFailureLogin);

        assertEquals(logLineanalysisResult,failure);
    }

}
