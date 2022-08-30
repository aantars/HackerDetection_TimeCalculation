package com.hotelbeds.supplierintegrations.hackertest.detector.exception;

public class UpdateListException extends RuntimeException {
    public UpdateListException(String deleteProblem) {
        super(deleteProblem);
    }
}
