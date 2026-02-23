package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PatientAlertSlideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_alert_slide);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            Intent intent = new Intent(PatientAlertSlideActivity.this, AlertSlideActivity.class);
            startActivity(intent);
        });

        // Get patient details from intent
        Intent intent = getIntent();
        String patientName = intent.getStringExtra("patientName");
        int patientImageResId = intent.getIntExtra("patientImage", R.drawable.ic_profile);

        // Set patient details to the views
        TextView patientNameTextView = findViewById(R.id.patient_name);
        ImageView patientImageView = findViewById(R.id.patient_image);
        TextView alertDetailsTextView = findViewById(R.id.alert_details);


        patientNameTextView.setText("Patient: " + patientName);
        patientImageView.setImageResource(patientImageResId);
        alertDetailsTextView.setText("The radiation dose for the CT scan of " + patientName + " exceeded the moderate threshold by 20%. Please review the scan parameters and acknowledge the alert.");


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_alerts);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(PatientAlertSlideActivity.this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(PatientAlertSlideActivity.this, ListOfPatientsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_scans) {
                // Assuming you have a ScansActivity
                // startActivity(new Intent(PatientAlertSlide.this, ScansActivity.class));
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(PatientAlertSlideActivity.this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                // Assuming you have an AdminActivity
                // startActivity(new Intent(PatientAlertSlide.this, AdminActivity.class));
                return true;
            }
            return false;
        });
    }
}
