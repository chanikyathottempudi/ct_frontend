package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_patient);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> finish());

        String patientName = getIntent().getStringExtra("patientName");
        String patientId = getIntent().getStringExtra("patientId");
        int patientImageResId = getIntent().getIntExtra("patientImageResId", R.drawable.ic_profile);

        TextView nameTextView = findViewById(R.id.patient_name);
        TextView idTextView = findViewById(R.id.patient_id);
        CircleImageView profileImageView = findViewById(R.id.patient_profile_image);

        nameTextView.setText(patientName);
        idTextView.setText("Patient ID: " + patientId);
        profileImageView.setImageResource(patientImageResId);

        Button historyButton = findViewById(R.id.history_button);
        historyButton.setOnClickListener(v -> {
            Intent intent = new Intent(PatientDetails.this, ExamHistory.class);
            startActivity(intent);
        });

        Button graphsButton = findViewById(R.id.graphs_button);
        graphsButton.setOnClickListener(v -> {
            Intent intent = new Intent(PatientDetails.this, PatientGraphActivity.class);
            intent.putExtra("patientName", patientName);
            startActivity(intent);
        });

        Button summaryButton = findViewById(R.id.summary_button);
        summaryButton.setOnClickListener(v -> {
            Intent intent = new Intent(PatientDetails.this, PatientSummaryActivity.class);
            intent.putExtra("patientName", patientName);
            startActivity(intent);
        });

        Button monitorButton = findViewById(R.id.monitor_button);
        monitorButton.setOnClickListener(v -> {
            Intent intent = new Intent(PatientDetails.this, RealTimeMonitor.class);
            intent.putExtra("patientName", patientName);
            startActivity(intent);
        });

        Button registerScanButton = findViewById(R.id.register_new_scan_button);
        registerScanButton.setOnClickListener(v -> {
            Intent intent = new Intent(PatientDetails.this, NewScanRegistrationActivity.class);
            startActivity(intent);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_patients);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(PatientDetails.this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                // Already on a patient-related screen, do nothing
                return true;
            } else if (itemId == R.id.navigation_scans) {
                startActivity(new Intent(PatientDetails.this, NewScanRegistrationActivity.class));
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(PatientDetails.this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                // startActivity(new Intent(PatientDetails.this, AdminActivity.class));
                return true;
            }
            return false;
        });
    }
}