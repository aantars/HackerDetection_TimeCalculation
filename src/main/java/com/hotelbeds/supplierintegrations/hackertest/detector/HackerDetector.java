package com.hotelbeds.supplierintegrations.hackertest.detector;

import org.springframework.stereotype.Component;

public interface HackerDetector {

    String parseLine(String line);
}
