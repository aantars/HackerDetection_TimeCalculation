package com.hotelbeds.supplierintegrations.hackertest.detector.tool;

import com.hotelbeds.supplierintegrations.hackertest.detector.exception.UnableToSaveFailureException;
import com.hotelbeds.supplierintegrations.hackertest.detector.exception.UpdateListException;
import com.hotelbeds.supplierintegrations.hackertest.detector.model.FailureAttempt;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AttemptsManagerImpl implements AttemptsManager{

    private static Map<String, FailureAttempt> failedIPLoggingAttemptsList = new ConcurrentHashMap<>();
    private static int deadLineTimeInMinutes = 5;

    @Override
    public void init() {
        this.failedIPLoggingAttemptsList = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isAttackCandidate(FailureAttempt failureAttempt) {
        removeDeprecatedAttempts();

        if(getPreviousAttempts(failureAttempt) >= 5){
            deleteAttackCandidateFromList(failureAttempt);
            return true;
        }
        return false;
    }

    private synchronized void deleteAttackCandidateFromList(FailureAttempt failureAttempt) {
        try{
            failedIPLoggingAttemptsList.remove(failureAttempt.getIpAddress());
        }
        catch (Exception e){
            throw new UpdateListException("Unable to delete the attack candidate Failure attempts from list");
        }
    }

    @Override
    public synchronized boolean removeDeprecatedAttempts() {
        try{
            failedIPLoggingAttemptsList.entrySet().stream().
                filter(failureAttempt ->
                        failureAttempt.getValue().getTriggeredTimeStamp().
                                isBefore(getCurrentAttemptsDeadLineTime()))
                .forEach(failureAttempt -> failedIPLoggingAttemptsList.remove(failureAttempt.getKey()));

        return true;
        } catch (Exception e){
            throw new UpdateListException("Unable to delete the old attempts");
        }
    }

    private LocalDateTime getCurrentAttemptsDeadLineTime() {
        LocalDateTime deadLineTime = LocalDateTime.now().minusMinutes(deadLineTimeInMinutes);
        return deadLineTime;
    }

    private int getPreviousAttempts(FailureAttempt failureAttempt) {
        FailureAttempt existingFailureAttempt = failedIPLoggingAttemptsList.get(failureAttempt.getIpAddress());
        if(existingFailureAttempt == null){
            Optional<FailureAttempt> savedFailureAttempt = saveFailureAttempt(failureAttempt);
            return savedFailureAttempt.get().getAttemptsAmount();
        }
        else{
            existingFailureAttempt = updateAttemptsAmount(existingFailureAttempt).get();
        }
        return existingFailureAttempt.getAttemptsAmount();
    }

    private Optional<FailureAttempt> updateAttemptsAmount(FailureAttempt existingFailureAttempt) {
        existingFailureAttempt.setAttemptsAmount(existingFailureAttempt.getAttemptsAmount()+1);
        return saveFailureAttempt(existingFailureAttempt);
    }

    private Optional<FailureAttempt> saveFailureAttempt(FailureAttempt failureAttempt) {
        try{
            this.failedIPLoggingAttemptsList.put(failureAttempt.getIpAddress(),failureAttempt);
            return Optional.of(failureAttempt);
        } catch (Exception e){
            throw new UnableToSaveFailureException("Unable to store the FailureAttempt");
        }
    }
}
