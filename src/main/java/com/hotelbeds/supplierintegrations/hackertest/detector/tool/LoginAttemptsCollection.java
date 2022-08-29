package com.hotelbeds.supplierintegrations.hackertest.detector.tool;

import com.hotelbeds.supplierintegrations.hackertest.detector.model.FailureAttempt;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginAttemptsCollection {

    private Map<String, FailureAttempt> failedIPLoggingAttemptsList = new ConcurrentHashMap<>();

    public Map<String, FailureAttempt> getFailedIPLoggingAttemptsList() {
        return failedIPLoggingAttemptsList;
    }

    public void setFailedIPLoggingAttemptsList(Map<String, FailureAttempt> failedIPLoggingAttemptsList) {
        this.failedIPLoggingAttemptsList = failedIPLoggingAttemptsList;
    }
}
