package com.simats.finalapp;

public class Alert {
    private String patientName;
    private String roomNumber;
    private int imageResId;

    public Alert(String patientName, String roomNumber, int imageResId) {
        this.patientName = patientName;
        this.roomNumber = roomNumber;
        this.imageResId = imageResId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getImageResId() {
        return imageResId;
    }
}
