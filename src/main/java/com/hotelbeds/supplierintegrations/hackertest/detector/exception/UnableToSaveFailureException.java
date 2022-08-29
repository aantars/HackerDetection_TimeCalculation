package com.hotelbeds.supplierintegrations.hackertest.detector.exception;

public class UnableToSaveFailureException extends RuntimeException {
    public UnableToSaveFailureException(String failureNotSaved) {
        super(failureNotSaved);
    }
}
