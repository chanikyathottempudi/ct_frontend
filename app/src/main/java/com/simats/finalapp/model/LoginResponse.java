package com.simats.finalapp.model;

public class LoginResponse {
    private boolean success;
    private String access;
    private String refresh;
    private User user;
    private String error;

    public boolean isSuccess() { return success; }
    public String getAccess() { return access; }
    public String getRefresh() { return refresh; }
    public User getUser() { return user; }
    public String getError() { return error; }
}
