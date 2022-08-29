package com.hotelbeds.supplierintegrations.hackertest.detector.exception;

public class WrongLogLineFormatException extends RuntimeException {
    public WrongLogLineFormatException(String wrongFormat) {
        super(wrongFormat);
    }
}
