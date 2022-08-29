package com.hotelbeds.supplierintegrations.hackertest.detector.tool;

import com.hotelbeds.supplierintegrations.hackertest.detector.exception.UnableToSaveFailureException;
import com.hotelbeds.supplierintegrations.hackertest.detector.model.FailureAttempt;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AttemptsManagerImpl implements AttemptsManager{

    private static Map<String, FailureAttempt> failedIPLoggingAttemptsList = new ConcurrentHashMap<>();

    @Override
    public void init() {
        this.failedIPLoggingAttemptsList = new ConcurrentHashMap<>();
    }

    @Override
    public int checkPreviousAttempsByIP(String internetAddress) {
        return 1;
    }

    @Override
    public Optional<FailureAttempt> saveFailureAttempt(String internetAddress,FailureAttempt failureAttempt) {
        this.failedIPLoggingAttemptsList.put(internetAddress,failureAttempt);
        /*try{
            return Optional.of(this.failedIPLoggingAttemptsList.put(internetAddress,failureAttempt));
        } catch (Exception e){
            throw new UnableToSaveFailureException("Unable to store the FailureAttempt");
        }*/

        return Optional.empty();
    }
}
