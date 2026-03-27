package com.simats.finalapp.model;

public class SignupRequest {
    private String full_name;
    private String username;
    private String email;
    private String role;
    private String password;

    public SignupRequest(String full_name, String username, String email, String role, String password) {
        this.full_name = full_name;
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
    }
}
