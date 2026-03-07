package com.simats.finalapp;

import java.util.ArrayList;
import java.util.List;

public class PatientManager {
    private static PatientManager instance;
    private final List<Patient> patientList;

    private PatientManager() {
        patientList = new ArrayList<>();
        
        // Mock data with previous scans
        Patient p1 = new Patient("Aarav Sharma", "123456789", "Male", R.drawable.men_icon);
        p1.addScanUri("res/img_12");
        p1.addScanUri("res/img_13");
        patientList.add(p1);

        Patient p2 = new Patient("Ishani Gupta", "987654321", "Female", R.drawable.women_icon);
        p2.addScanUri("res/img_14");
        p2.addScanUri("res/img_15");
        patientList.add(p2);

        Patient p3 = new Patient("Vihaan Malhotra", "456789123", "Male", R.drawable.men_icon);
        p3.addScanUri("res/img_16");
        patientList.add(p3);

        Patient p4 = new Patient("Diya Iyer", "789123456", "Female", R.drawable.women_icon);
        p4.addScanUri("res/img_17");
        p4.addScanUri("res/img_18");
        patientList.add(p4);

        Patient p5 = new Patient("Advait Joshi", "321654987", "Male", R.drawable.men_icon);
        p5.addScanUri("res/img_19");
        patientList.add(p5);

        // Adding more patients
        patientList.add(new Patient("Rajesh Kumar", "P106", "Male", R.drawable.men_icon));
        patientList.add(new Patient("Priya Nair", "P107", "Female", R.drawable.women_icon));
        patientList.add(new Patient("Arjun Singh", "P108", "Male", R.drawable.men_icon));
        patientList.add(new Patient("Anjali Reddy", "P109", "Female", R.drawable.women_icon));
        patientList.add(new Patient("Vikram Patil", "P110", "Male", R.drawable.men_icon));
        patientList.add(new Patient("Sunita Deshmukh", "P111", "Female", R.drawable.women_icon));
        patientList.add(new Patient("Suresh Pillai", "P112", "Male", R.drawable.men_icon));
        patientList.add(new Patient("Kavita Hegde", "P113", "Female", R.drawable.women_icon));
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
