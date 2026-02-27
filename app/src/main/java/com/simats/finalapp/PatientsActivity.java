package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PatientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_patients);

        findViewById(R.id.back_arrow).setOnClickListener(v -> finish());

        ListView patientsListView = findViewById(R.id.patients_list_view);

        ArrayList<Patient> patients = new ArrayList<>();
        patients.add(new Patient("Ethan Carter", "123456789", "Male", R.drawable.men_icon));
        patients.add(new Patient("Sophia Clark", "987654321", "Female", R.drawable.women_icon));
        patients.add(new Patient("Liam Davis", "456789123", "Male", R.drawable.men_icon));
        patients.add(new Patient("Olivia Evans", "789123456", "Female", R.drawable.women_icon));
        patients.add(new Patient("Noah Foster", "321654987", "Male", R.drawable.men_icon));

        PatientAdapter adapter = new PatientAdapter(this, patients);
        patientsListView.setAdapter(adapter);

        patientsListView.setOnItemClickListener((parent, view, position, id) -> {
            Patient selectedPatient = patients.get(position);
            Intent intent = new Intent(PatientsActivity.this, PatientDetails.class);
            intent.putExtra("patientName", selectedPatient.getName());
            intent.putExtra("patientId", selectedPatient.getId());
            intent.putExtra("patientGender", selectedPatient.getGender());
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
                // Current screen, do nothing
                return true;
            } else if (itemId == R.id.navigation_scans) {
                startActivity(new Intent(this, NewScanRegistrationActivity.class));
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                // startActivity(new Intent(this, AdminActivity.class));
                return true;
            }
            return false;
        });

        FloatingActionButton fabAddPatient = findViewById(R.id.fab_add_patient);
        fabAddPatient.setOnClickListener(view -> {
            Intent intent = new Intent(PatientsActivity.this, RegisterPatient.class);
            startActivity(intent);
        });
    }
}
