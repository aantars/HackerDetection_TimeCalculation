package com.hotelbeds.supplierintegrations.hackertest.detector;

import com.hotelbeds.supplierintegrations.hackertest.detector.exception.NullLogLineException;
import com.hotelbeds.supplierintegrations.hackertest.detector.model.FailureAttempt;
import com.hotelbeds.supplierintegrations.hackertest.detector.tool.LineStructureProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HackerDetectorImpl implements HackerDetector{

    private LineStructureProcessor lineStructureProcessor;

    public HackerDetectorImpl(LineStructureProcessor lineStructureProcessor) {
        this.lineStructureProcessor = lineStructureProcessor;
    }

    @Override
    public String parseLine(String line) {

        if(line == null){
            throw new NullLogLineException("Line is null");
        }

        Optional<FailureAttempt> failureAttempt = lineStructureProcessor.validateLineAndReturnAsObject(line);

        if(failureAttempt.isPresent()){
            // check if is an attack

         }

        return null;
    }
}
