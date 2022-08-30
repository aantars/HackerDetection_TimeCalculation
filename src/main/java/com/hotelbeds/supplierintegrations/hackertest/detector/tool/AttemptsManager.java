package com.hotelbeds.supplierintegrations.hackertest.detector.tool;

import com.hotelbeds.supplierintegrations.hackertest.detector.model.FailureAttempt;

import java.util.Optional;

public interface AttemptsManager {

    void init();
    boolean isAttackCandidate(FailureAttempt failureAttempt);
    boolean removeDeprecatedAttempts();
}
