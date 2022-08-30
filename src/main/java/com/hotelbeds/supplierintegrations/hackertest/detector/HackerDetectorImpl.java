package com.hotelbeds.supplierintegrations.hackertest.detector;

import com.hotelbeds.supplierintegrations.hackertest.detector.exception.NullLogLineException;
import com.hotelbeds.supplierintegrations.hackertest.detector.model.FailureAttempt;
import com.hotelbeds.supplierintegrations.hackertest.detector.tool.AttemptsManager;
import com.hotelbeds.supplierintegrations.hackertest.detector.tool.LineStructureProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HackerDetectorImpl implements HackerDetector{

    private LineStructureProcessor lineStructureProcessor;
    private AttemptsManager attemptsManager;

    @Autowired
    public HackerDetectorImpl(LineStructureProcessor lineStructureProcessor, AttemptsManager attemptsManager) {
        this.lineStructureProcessor = lineStructureProcessor;
        this.attemptsManager = attemptsManager;
    }

    @Override
    public String parseLine(String line) {

        if(line == null){
            throw new NullLogLineException("Line is null");
        }

        Optional<FailureAttempt> failureAttempt = lineStructureProcessor.validateLineAndReturnAsObject(line);

        if(failureAttempt.isPresent() && attemptsManager.isAttackCandidate(failureAttempt.get())){
            return failureAttempt.get().getIpAddress();
         }

        return null;
    }

    public void init(){attemptsManager.init();}
}
