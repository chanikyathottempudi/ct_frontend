package com.simats.finalapp.model;

public class VerificationRequest {
    private String email;
    private String code;

    public VerificationRequest(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public String getEmail() { return email; }
    public String getCode() { return code; }
}
