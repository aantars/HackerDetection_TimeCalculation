package com.hotelbeds.supplierintegrations.hackertest.detector.exception;

public class TimeStampWrongFormatException extends RuntimeException {
    public TimeStampWrongFormatException(String wrongTimestamp) {
        super(wrongTimestamp);
    }
}
