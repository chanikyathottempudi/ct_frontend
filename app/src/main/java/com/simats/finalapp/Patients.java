package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.simats.finalapp.Patient;

import java.util.ArrayList;

public class Patients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patients_list);

        findViewById(R.id.back_arrow).setOnClickListener(v -> finish());

        ListView patientsListView = findViewById(R.id.patients_list_view);

        ArrayList<Patient> patients = new ArrayList<>();
        patients.add(new Patient("Ethan Carter", "123456789", "Male", R.drawable.ic_profile));
        patients.add(new Patient("Sophia Clark", "987654321", "Female", R.drawable.ic_profile));
        patients.add(new Patient("Liam Davis", "456789123", "Male", R.drawable.ic_profile));
        patients.add(new Patient("Olivia Evans", "789123456", "Female", R.drawable.ic_profile));
        patients.add(new Patient("Noah Foster", "321654987", "Male", R.drawable.ic_profile));

        PatientAdapter adapter = new PatientAdapter(this, patients);
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
            if (item.getItemId() == R.id.navigation_dashboard) {
                finish();
                return true;
            }
            return false;
        });

        FloatingActionButton fabAddPatient = findViewById(R.id.fab_add_patient);
        fabAddPatient.setOnClickListener(view -> {
            Intent intent = new Intent(Patients.this, RegisterPatient.class);
            startActivity(intent);
        });
    }
}
