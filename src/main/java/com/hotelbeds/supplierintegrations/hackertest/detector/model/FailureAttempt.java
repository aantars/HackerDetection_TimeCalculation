package com.hotelbeds.supplierintegrations.hackertest.detector.model;

import java.time.LocalDateTime;

public class FailureAttempt {

    private String ipAddress;
    private LocalDateTime triggeredTimeStamp;
    private String userName;

    public FailureAttempt() {
    }

    public FailureAttempt(String ipAddress, LocalDateTime triggeredTimeStamp, String userName) {
        this.ipAddress = ipAddress;
        this.triggeredTimeStamp = triggeredTimeStamp;
        this.userName = userName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getTriggeredTimeStamp() {
        return triggeredTimeStamp;
    }

    public void setTriggeredTimeStamp(LocalDateTime triggeredTimeStamp) {
        this.triggeredTimeStamp = triggeredTimeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
