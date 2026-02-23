package com.simats.finalapp;

public class Patient {
    private String name;
    private String id;
    private int imageResId;

    public Patient(String name, String id, int imageResId) {
        this.name = name;
        this.id = id;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getImageResId() {
        return imageResId;
    }
}
