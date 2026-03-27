package com.simats.finalapp.model;

import java.util.List;

public class PatientResponse {
    private int id;
    private String patient_id;
    private String name;
    private String gender;
    private Integer age;
    private String dob;
    private String room_number;
    private String allergies;
    private String clinical_notes;

    public int getServerId() { return id; }
    public String getPatientId() { return patient_id; }
    public String getName() { return name; }
    public String getGender() { return gender; }
    public Integer getAge() { return age; }
    public String getDob() { return dob; }
    public String getRoomNumber() { return room_number; }
    public String getAllergies() { return allergies; }
    public String getClinicalNotes() { return clinical_notes; }
}
