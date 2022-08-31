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
import java.util.stream.Collectors;

@Component
public class LineStructureProcessorImpl implements LineStructureProcessor{

    private static int defaultAttemptsAmount = 1;
    public enum SigninStatus{
        SIGNIN_FAILURE
    }

    public LineStructureProcessorImpl() {
    }

    @Override
    public Optional<FailureAttempt> validateLineAndReturnAsObject(String givenLogLine) {

        if(isEmptyLine(givenLogLine)){
            throw new EmptyLogLineException("Log line is empty");
        }

        return parseLineToFailureAttemptInstance(givenLogLine);
    }

    private Boolean isEmptyLine(String givenLine){
        return givenLine.equalsIgnoreCase("");
    }

    private Optional<FailureAttempt> parseLineToFailureAttemptInstance(String givenLogLine){
        ArrayList<String> splittedLine = getSpplittedLogLine(givenLogLine);

        if(splittedLine.size()!=4){
            throw new WrongLogLineFormatException("Wrong log line format");
        }

        if(!isSigninFailure(splittedLine.get(2))){
            return Optional.empty();
        }

        FailureAttempt failureAttempt =
                new FailureAttempt(splittedLine.get(0), parseEpochToLocalDateTime(splittedLine.get(1)), splittedLine.get(3),defaultAttemptsAmount);

        return Optional.of(failureAttempt);
    }

    private ArrayList<String> getSpplittedLogLine(String givenLogLine) {

        String temporalSplittedList[]  = givenLogLine.split(",");

        return Arrays.stream(temporalSplittedList).sequential().collect(Collectors.toCollection(ArrayList::new));
    }

    private LocalDateTime parseEpochToLocalDateTime(String epochTime){
        try {
            long epochLongFormat = Long.parseLong(epochTime);
            return Instant.ofEpochSecond(epochLongFormat).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        catch (NumberFormatException e){
            throw new TimeStampWrongFormatException("Timestamp format is wrong");
        }
    }

    private Boolean isSigninFailure(String signinCode){
        return SigninStatus.SIGNIN_FAILURE.name().equals(signinCode);
    }

}
