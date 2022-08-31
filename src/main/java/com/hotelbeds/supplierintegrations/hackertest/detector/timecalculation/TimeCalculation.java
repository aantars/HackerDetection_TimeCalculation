package com.hotelbeds.supplierintegrations.hackertest.detector.timecalculation;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class TimeCalculation {

    // INFO: RFC-1123 updates RFC-822
    private static DateTimeFormatter dateFormat = DateTimeFormatter.RFC_1123_DATE_TIME;

    public long getMinutesBetweenTwoTimeStamps(String earlierTimeStamp, String laterTimeStamp) {

        Duration duration = getDurationBetweenTwoTimeStamps(earlierTimeStamp, laterTimeStamp);
        long differenceBetweenTimeStamps = duration.getSeconds()/60;

        return differenceBetweenTimeStamps >0 ? differenceBetweenTimeStamps:0;
    }

    private Duration getDurationBetweenTwoTimeStamps(String earlierTimeStamp, String laterTimeStamp) {

        ZonedDateTime   zonedParsedEarlierTimeStamp = getZonedDateTimeValueOfRFC(earlierTimeStamp);
        ZonedDateTime   zonedParsedLaterTimeStamp = getZonedDateTimeValueOfRFC(laterTimeStamp);

        return Duration.between(zonedParsedEarlierTimeStamp, zonedParsedLaterTimeStamp);
    }

    private ZonedDateTime getZonedDateTimeValueOfRFC(String timeStamp){
        try {
            return ZonedDateTime.parse(timeStamp, dateFormat);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("DateTime format not accepted"); }
    }

}