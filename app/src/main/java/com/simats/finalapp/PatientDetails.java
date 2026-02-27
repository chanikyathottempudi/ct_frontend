package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PatientDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_patient);

        findViewById(R.id.back_arrow).setOnClickListener(v -> finish());

        Intent intent = getIntent();
        String patientName = intent.getStringExtra("patientName");
        String patientId = intent.getStringExtra("patientId");
        String patientGender = intent.getStringExtra("patientGender");
        int patientImageResId = intent.getIntExtra("patientImageResId", R.drawable.ic_profile);

        ImageView patientProfileImage = findViewById(R.id.patient_profile_image);
        TextView patientNameTextView = findViewById(R.id.patient_name);
        TextView patientInfoTextView = findViewById(R.id.patient_info);
        TextView patientIdTextView = findViewById(R.id.patient_id);

        patientProfileImage.setImageResource(patientImageResId);
        patientNameTextView.setText(patientName);
        patientInfoTextView.setText(patientGender + ", 32");
        patientIdTextView.setText("Patient ID: " + patientId);

        findViewById(R.id.history_button).setOnClickListener(v -> {
            Intent historyIntent = new Intent(PatientDetails.this, ExamHistory.class);
            historyIntent.putExtra("patientName", patientName);
            startActivity(historyIntent);
        });

        findViewById(R.id.graphs_button).setOnClickListener(v -> {
            Intent graphsIntent = new Intent(PatientDetails.this, PatientGraphActivity.class);
            graphsIntent.putExtra("patientName", patientName);
            startActivity(graphsIntent);
        });

        findViewById(R.id.summary_button).setOnClickListener(v -> {
            Intent summaryIntent = new Intent(PatientDetails.this, PatientSummaryActivity.class);
            summaryIntent.putExtra("patientName", patientName);
            startActivity(summaryIntent);
        });

        findViewById(R.id.monitor_button).setOnClickListener(v -> {
            Intent monitorIntent = new Intent(PatientDetails.this, RealTimeMonitor.class);
            startActivity(monitorIntent);
        });

        findViewById(R.id.register_new_scan_button).setOnClickListener(v -> {
            Intent registerScanIntent = new Intent(PatientDetails.this, NewScanRegistrationActivity.class);
            startActivity(registerScanIntent);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_patients);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(this, ListOfPatientsActivity.class));
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
    }
}
