package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Patients extends AppCompatActivity {

    private static final int ADD_PATIENT_REQUEST = 1;
    private ArrayList<Patient> patients;
    private PatientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_patients);

        findViewById(R.id.back_arrow).setOnClickListener(v -> finish());

        ListView patientsListView = findViewById(R.id.patients_list_view);

        patients = new ArrayList<>();
        // Original 5
        patients.add(new Patient("Ethan Carter", "123456789", "Male", R.drawable.men_icon));
        patients.add(new Patient("Sophia Clark", "987654321", "Female", R.drawable.women_icon));
        patients.add(new Patient("Liam Davis", "456789123", "Male", R.drawable.men_icon));
        patients.add(new Patient("Olivia Evans", "789123456", "Female", R.drawable.women_icon));
        patients.add(new Patient("Noah Foster", "321654987", "Male", R.drawable.men_icon));

        // 8 New Patients as requested
        patients.add(new Patient("David Miller", "P106", "Male", R.drawable.men_icon));
        patients.add(new Patient("Sarah Taylor", "P107", "Female", R.drawable.women_icon));
        patients.add(new Patient("James Anderson", "P108", "Male", R.drawable.men_icon));
        patients.add(new Patient("Linda Thomas", "P109", "Female", R.drawable.women_icon));
        patients.add(new Patient("Charles Jackson", "P110", "Male", R.drawable.men_icon));
        patients.add(new Patient("Barbara White", "P111", "Female", R.drawable.women_icon));
        patients.add(new Patient("William Harris", "P112", "Male", R.drawable.men_icon));
        patients.add(new Patient("Susan Martin", "P113", "Female", R.drawable.women_icon));

        adapter = new PatientAdapter(this, patients);
        patientsListView.setAdapter(adapter);

        patientsListView.setOnItemClickListener((parent, view, position, id) -> {
            Patient selectedPatient = patients.get(position);
            Intent intent = new Intent(Patients.this, PatientDetails.class);
            intent.putExtra("patientName", selectedPatient.getName());
            intent.putExtra("patientId", selectedPatient.getId());
            intent.putExtra("patientImageResId", selectedPatient.getImageResId());
            startActivity(intent);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_patients);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                return true;
            } else if (itemId == R.id.navigation_scans) {
                startActivity(new Intent(this, NewScanRegistrationActivity.class));
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                startActivity(new Intent(this, AdminControlCenterActivity.class));
                return true;
            }
            return false;
        });

        FloatingActionButton fabAddPatient = findViewById(R.id.fab_add_patient);
        fabAddPatient.setOnClickListener(view -> {
            Intent intent = new Intent(Patients.this, RegisterPatient.class);
            startActivityForResult(intent, ADD_PATIENT_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_PATIENT_REQUEST && resultCode == RESULT_OK && data != null) {
            String fullName = data.getStringExtra("fullName");
            String patientId = data.getStringExtra("patientId");
            String gender = data.getStringExtra("gender");

            int imageResId = R.drawable.men_icon; // Default
            if ("Female".equalsIgnoreCase(gender)) {
                imageResId = R.drawable.women_icon;
            }

            Patient newPatient = new Patient(fullName, patientId, gender, imageResId);
            patients.add(newPatient);
            adapter.notifyDataSetChanged();
        }
    }
}
