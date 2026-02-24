package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DoseStatisticsPatientDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dose_statistics_patient_details);

        findViewById(R.id.back_arrow).setOnClickListener(v -> finish());

        String patientName = getIntent().getStringExtra("patientName");

        TextView patientNameTextView = findViewById(R.id.patient_name_dose_details);
        patientNameTextView.setText(patientName + "'s Dose Statistics");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(DoseStatisticsPatientDetailsActivity.this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(DoseStatisticsPatientDetailsActivity.this, ListOfPatientsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_scans) {
                startActivity(new Intent(DoseStatisticsPatientDetailsActivity.this, NewScanRegistrationActivity.class));
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(DoseStatisticsPatientDetailsActivity.this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                // startActivity(new Intent(DoseStatisticsPatientDetailsActivity.this, AdminActivity.class));
                return true;
            }
            return false;
        });
    }
}
