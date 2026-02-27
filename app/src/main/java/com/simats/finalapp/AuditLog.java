package com.simats.finalapp;

public class AuditLog {
    private String timestamp;
    private String user;
    private String action;

    public AuditLog(String timestamp, String user, String action) {
        this.timestamp = timestamp;
        this.user = user;
        this.action = action;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }

    public String getAction() {
        return action;
    }
}
