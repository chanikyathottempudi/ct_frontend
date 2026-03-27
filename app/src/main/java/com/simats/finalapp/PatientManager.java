package com.simats.finalapp;

import com.simats.finalapp.model.PatientResponse;
import com.simats.finalapp.network.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientManager {
    private static PatientManager instance;
    private final List<Patient> patientList;

    private PatientManager() {
        patientList = new ArrayList<>();
        
        // Mock data removed in favor of backend sync, 
        // but we'll leave it for initial state if needed.
        initializeMockData();
    }

    private void initializeMockData() {
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

    public void syncWithBackend(Runnable onComplete) {
        RetrofitClient.getApiService().getPatients().enqueue(new Callback<List<PatientResponse>>() {
            @Override
            public void onResponse(Call<List<PatientResponse>> call, Response<List<PatientResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Simple replacement to ensure consistency with backend
                    patientList.clear();
                    for (PatientResponse r : response.body()) {
                        int img = "Female".equalsIgnoreCase(r.getGender()) ? R.drawable.women_icon : R.drawable.men_icon;
                        patientList.add(new Patient(r.getName(), r.getPatientId(), r.getGender(), img, r.getAllergies()));
                    }
                }
                if (onComplete != null) onComplete.run();
            }

            @Override
            public void onFailure(Call<List<PatientResponse>> call, Throwable t) {
                if (onComplete != null) onComplete.run();
            }
        });
    }
}
