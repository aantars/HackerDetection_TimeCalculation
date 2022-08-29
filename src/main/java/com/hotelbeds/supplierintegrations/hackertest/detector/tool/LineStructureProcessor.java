package com.hotelbeds.supplierintegrations.hackertest.detector.tool;

import com.hotelbeds.supplierintegrations.hackertest.detector.model.FailureAttempt;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface LineStructureProcessor {

    Optional<FailureAttempt> validateLineAndReturnAsObject(String anyString);
}
