package com.hotelbeds.supplierintegrations.hackertest.detector;

import org.springframework.stereotype.Component;

@Component
public class HackerDetectorImpl implements HackerDetector{

    @Override
    public String parseLine(String line) {

        // if "Optional<FailureAttempt>" processedLine.isEmpty() return null

        return "127.0.0.1";
    }
}
