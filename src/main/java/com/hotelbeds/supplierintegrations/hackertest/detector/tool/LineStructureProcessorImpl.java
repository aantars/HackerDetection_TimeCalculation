package com.hotelbeds.supplierintegrations.hackertest.detector.tool;

import com.hotelbeds.supplierintegrations.hackertest.detector.exception.TimeStampWrongFormatException;
import com.hotelbeds.supplierintegrations.hackertest.detector.exception.WrongLogLineFormatException;
import com.hotelbeds.supplierintegrations.hackertest.detector.exception.EmptyLogLineException;
import com.hotelbeds.supplierintegrations.hackertest.detector.model.FailureAttempt;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Component
public class LineStructureProcessorImpl implements LineStructureProcessor{


    @Override
    public Optional<FailureAttempt> validateLineAndReturnAsObject(String givenLine) {
        Optional<FailureAttempt> failureAttempt = Optional.empty();
        if(isEmptyLine(givenLine)){
            throw new EmptyLogLineException("Log line is empty");
        }

        return parseLineToFailureAttemptInstance(givenLine);

    }

    private Boolean isEmptyLine(String givenLine){
        return givenLine.equalsIgnoreCase("");
    }

    private Optional<FailureAttempt> parseLineToFailureAttemptInstance(String givenLine){
        ArrayList<String> splittedLine = (ArrayList<String>) Arrays.asList(givenLine.split(","));

        if(splittedLine.size()!=4){
            throw new WrongLogLineFormatException("Wrong log line format");
        }

        if(!isSigninFailure(splittedLine.get(2))){
            return Optional.empty();
        }

        FailureAttempt failureAttempt =
                new FailureAttempt(splittedLine.get(0), getReadableTimeStamp(splittedLine.get(1)), splittedLine.get(3));

        return Optional.of(failureAttempt);
    }

    private LocalDateTime getReadableTimeStamp(String epochTime){
        try {
            long epochLongFormat = Long.parseLong(epochTime);
            return Instant.ofEpochMilli(epochLongFormat).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        catch (NumberFormatException e){
            throw new TimeStampWrongFormatException("Timestamp format is wrong");
        }
    }

    private Boolean isSigninFailure(String signinCode){
        return signinCode.equalsIgnoreCase("SIGNIN_FAILURE")? true : false;
    }

}