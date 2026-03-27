package com.simats.finalapp.model;

public class PatientRequest {
    private String name;
    private String patient_id;
    private String clinical_notes;
    private String gender;
    private String dob;
    private String allergies;

    public PatientRequest(String name, String patient_id, String clinical_notes, String gender) {
        this(name, patient_id, clinical_notes, gender, null, "None");
    }

    public PatientRequest(String name, String patient_id, String clinical_notes, String gender, String dob, String allergies) {
        this.name = name;
        this.patient_id = patient_id;
        this.clinical_notes = clinical_notes;
        this.gender = gender;
        this.dob = dob;
        this.allergies = allergies;
    }
}
