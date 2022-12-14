package com.hotelbeds.supplierintegrations.hackertest.detector.tool;

import com.hotelbeds.supplierintegrations.hackertest.detector.model.FailureAttempt;

import java.util.Optional;

public interface LineStructureProcessor {

    Optional<FailureAttempt> validateLineAndReturnAsObject(String anyString);
}
