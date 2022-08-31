package com.hotelbeds.supplierintegrations.hackertest.detector.exception;

public class UnableToSaveFailureAttemptException extends RuntimeException {
    public UnableToSaveFailureAttemptException(String failureNotSaved) {
        super(failureNotSaved);
    }
}
