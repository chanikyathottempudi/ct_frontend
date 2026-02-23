package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.simats.finalapp.Patient;

import java.util.ArrayList;

public class ListOfPatientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_patients);

        findViewById(R.id.back_arrow).setOnClickListener(v -> {
            Intent intent = new Intent(ListOfPatientsActivity.this, Dashboard.class);
            startActivity(intent);
        });

        ListView patientsListView = findViewById(R.id.patients_list_view);

        ArrayList<Patient> patients = new ArrayList<>();
        patients.add(new Patient("Ethan Carter", "123456789", R.drawable.img_21));
        patients.add(new Patient("Sophia Clark", "987654321", R.drawable.img_22));
        patients.add(new Patient("Liam Davis", "456789123", R.drawable.img_23));
        patients.add(new Patient("Olivia Evans", "789123456", R.drawable.img_21));
        patients.add(new Patient("Noah Foster", "321654987", R.drawable.img_22));

        PatientAdapter adapter = new PatientAdapter(this, patients);
        patientsListView.setAdapter(adapter);

        patientsListView.setOnItemClickListener((parent, view, position, id) -> {
            Patient selectedPatient = patients.get(position);
            Intent intent = new Intent(ListOfPatientsActivity.this, PatientDetails.class);
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
                startActivity(new Intent(ListOfPatientsActivity.this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                // You are already in ListOfPatients, do nothing.
                return true;
            } else if (itemId == R.id.navigation_scans) {
                startActivity(new Intent(ListOfPatientsActivity.this, NewScanRegistrationActivity.class));
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(ListOfPatientsActivity.this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                // Assuming you have an AdminActivity
                // startActivity(new Intent(ListOfPatientsActivity.this, AdminActivity.class));
                return true;
            }
            return false;
        });

        FloatingActionButton fabAddPatient = findViewById(R.id.fab_add_patient);
        fabAddPatient.setOnClickListener(view -> {
            Intent intent = new Intent(ListOfPatientsActivity.this, RegisterPatient.class);
            startActivity(intent);
        });
    }
}