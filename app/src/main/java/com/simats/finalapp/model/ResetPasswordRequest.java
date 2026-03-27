package com.simats.finalapp.model;

public class ResetPasswordRequest {
    private String email;
    private String code;
    private String new_password;

    public ResetPasswordRequest(String email, String code, String new_password) {
        this.email = email;
        this.code = code;
        this.new_password = new_password;
    }
}
