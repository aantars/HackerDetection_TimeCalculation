package com.hotelbeds.supplierintegrations.hackertest.detector;

import org.springframework.stereotype.Component;

@Component
public interface HackerDetector {

    String parseLine(String line);
}
