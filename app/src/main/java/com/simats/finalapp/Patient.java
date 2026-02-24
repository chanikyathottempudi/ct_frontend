package com.simats.finalapp;

public class Patient {
    private String name;
    private String id;
    private String gender;
    private int imageResId;

    public Patient(String name, String id, String gender, int imageResId) {
        this.name = name;
        this.id = id;
        this.gender = gender;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public int getImageResId() {
        return imageResId;
    }
}
