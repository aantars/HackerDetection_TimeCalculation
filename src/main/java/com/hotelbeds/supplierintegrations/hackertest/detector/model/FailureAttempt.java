package com.hotelbeds.supplierintegrations.hackertest.detector.model;

import java.time.LocalDateTime;

public class FailureAttempt {

    private String ipAddress;
    private LocalDateTime triggeredTimeStamp;
    private String userName;
    private int attemptsAmount;

    public FailureAttempt(String ipAddress, LocalDateTime triggeredTimeStamp, String userName, int attemptsAmount) {
        this.ipAddress = ipAddress;
        this.triggeredTimeStamp = triggeredTimeStamp;
        this.userName = userName;
        this.attemptsAmount = attemptsAmount;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public LocalDateTime getTriggeredTimeStamp() {
        return triggeredTimeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public int getAttemptsAmount() {
        return attemptsAmount;
    }

    public void setAttemptsAmount(int attemptsAmount) {
        this.attemptsAmount = attemptsAmount;
    }
}
