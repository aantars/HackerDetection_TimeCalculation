package com.hotelbeds.supplierintegrations.hackertest.detector.tool;

import com.hotelbeds.supplierintegrations.hackertest.detector.model.FailureAttempt;

import java.util.Optional;

public interface AttemptsManager {

    void init();
    int checkPreviousAttempsByIP(String internetAddress);
    Optional<FailureAttempt> saveFailureAttempt(String internetAddress, FailureAttempt failureAttempt);
}
