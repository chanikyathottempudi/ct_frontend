package com.simats.finalapp;

import java.util.ArrayList;
import java.util.List;

public class PatientManager {
    private static PatientManager instance;
    private final List<Patient> patientList;

    private PatientManager() {
        patientList = new ArrayList<>();
        // Add initial dummy data
        patientList.add(new Patient("Ethan Carter", "123456789", "Male", R.drawable.men_icon));
        patientList.add(new Patient("Sophia Clark", "987654321", "Female", R.drawable.women_icon));
        patientList.add(new Patient("Liam Davis", "456789123", "Male", R.drawable.men_icon));
        patientList.add(new Patient("Olivia Evans", "789123456", "Female", R.drawable.women_icon));
        patientList.add(new Patient("Noah Foster", "321654987", "Male", R.drawable.men_icon));
    }

    public static synchronized PatientManager getInstance() {
        if (instance == null) {
            instance = new PatientManager();
        }
        return instance;
    }

    public List<Patient> getPatients() {
        return patientList;
    }

    public void addPatient(Patient patient) {
        patientList.add(patient);
    }
}
