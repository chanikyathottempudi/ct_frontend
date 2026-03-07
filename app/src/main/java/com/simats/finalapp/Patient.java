package com.simats.finalapp;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String name;
    private String id;
    private String gender;
    private int imageResId;
    private String uploadedFileUri; // Main profile or latest scan
    private String allergies;
    private List<String> scanUris; // List of all registered scans

    public Patient(String name, String id, String gender, int imageResId) {
        this.name = name;
        this.id = id;
        this.gender = gender;
        this.imageResId = imageResId;
        this.allergies = "None";
        this.scanUris = new ArrayList<>();
    }

    public Patient(String name, String id, String gender, int imageResId, String allergies) {
        this.name = name;
        this.id = id;
        this.gender = gender;
        this.imageResId = imageResId;
        this.allergies = allergies;
        this.scanUris = new ArrayList<>();
    }

    public Patient(String name, String id, String gender, int imageResId, String uploadedFileUri, String allergies) {
        this.name = name;
        this.id = id;
        this.gender = gender;
        this.imageResId = imageResId;
        this.uploadedFileUri = uploadedFileUri;
        this.allergies = allergies;
        this.scanUris = new ArrayList<>();
        if (uploadedFileUri != null) {
            this.scanUris.add(uploadedFileUri);
        }
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

    public String getUploadedFileUri() {
        return uploadedFileUri;
    }

    public void setUploadedFileUri(String uploadedFileUri) {
        this.uploadedFileUri = uploadedFileUri;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public List<String> getScanUris() {
        if (scanUris == null) scanUris = new ArrayList<>();
        return scanUris;
    }

    public void addScanUri(String uri) {
        if (scanUris == null) scanUris = new ArrayList<>();
        scanUris.add(uri);
        this.uploadedFileUri = uri; // Set as latest
    }

    public void removeScanUri(String uri) {
        if (scanUris != null) {
            scanUris.remove(uri);
            if (uri.equals(uploadedFileUri)) {
                uploadedFileUri = scanUris.isEmpty() ? null : scanUris.get(scanUris.size() - 1);
            }
        }
    }
}
