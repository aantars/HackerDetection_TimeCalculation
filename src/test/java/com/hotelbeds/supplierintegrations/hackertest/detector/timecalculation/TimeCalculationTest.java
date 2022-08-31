package com.hotelbeds.supplierintegrations.hackertest.detector.timecalculation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TimeCalculationTest {

    @Autowired
    TimeCalculation timeCalculation;

    @Test
    public void getMinutesBetweenTwoTimeStampsTest() {
        String earlierTimeStamp = "Thu, 21 Dec 2000 16:01:07 +0200";
        String laterTimeStamp = "Thu, 21 Dec 2000 16:07:06 +0200";

        long differenceInMinutes = timeCalculation.getMinutesBetweenTwoTimeStamps(earlierTimeStamp, laterTimeStamp);

        assertEquals( 5, differenceInMinutes);
    }

    @Test
    public void getMinutesBetweenTwoTimeStamps_finish_previous_to_start_test() {
        String firstTimeStampButLaterInTime = "Thu, 21 Dec 2000 16:06:37 +0200";
        String secondTimeStampButFirstInTime = "Thu, 21 Dec 2000 16:01:07 +0200";

        long differenceInMinutes = timeCalculation.getMinutesBetweenTwoTimeStamps(firstTimeStampButLaterInTime, secondTimeStampButFirstInTime);

        assertEquals(0,differenceInMinutes);
    }

    @Test
    public void wrongTimeStampFormat_exception_test(){
        String acceptedFormatTimeStamp = "Thu, 21 Dec 2000 16:06:37 +0200";
        String notAcceptedFormatTimeStamp = "Thu, 21 Dec 2000 16:01:07";

        assertThrows(IllegalArgumentException.class, () -> timeCalculation.getMinutesBetweenTwoTimeStamps(acceptedFormatTimeStamp, notAcceptedFormatTimeStamp));

    }



}
