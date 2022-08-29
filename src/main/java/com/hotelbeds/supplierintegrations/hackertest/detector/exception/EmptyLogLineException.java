package com.hotelbeds.supplierintegrations.hackertest.detector.exception;

public class EmptyLogLineException extends RuntimeException {
    public EmptyLogLineException(String emptyLog) {
        super(emptyLog);
    }
}
