package com.hotelbeds.supplierintegrations.hackertest.detector.exception;

public class NullLogLineException  extends RuntimeException{
    public NullLogLineException(String nullLine) {
        super(nullLine);
    }
}
