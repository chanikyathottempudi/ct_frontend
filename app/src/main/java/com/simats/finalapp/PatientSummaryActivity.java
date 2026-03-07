package com.simats.finalapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;
import java.util.Random;

public class PatientSummaryActivity extends AppCompatActivity {

    private View scanLine;
    private ImageView bodyScanImage;
    private TextView statusText;
    private TextView doseHead, doseChest, dosePelvis, doseAbdomen, doseLegs, doseArms;
    private String patientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_summary);

        // Initialize views
        bodyScanImage = findViewById(R.id.body_scan_image);
        scanLine = findViewById(R.id.scan_line);
        statusText = findViewById(R.id.ai_prediction_status);
        
        doseHead = findViewById(R.id.dose_head);
        doseChest = findViewById(R.id.dose_chest);
        dosePelvis = findViewById(R.id.dose_pelvis);
        doseAbdomen = findViewById(R.id.dose_abdomen);
        doseLegs = findViewById(R.id.dose_legs);
        doseArms = findViewById(R.id.dose_arms);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> finish());

        patientName = getIntent().getStringExtra("patientName");
        if (patientName == null) patientName = "Unknown Patient";

        TextView patientNameTextView = findViewById(R.id.patient_name_summary);
        patientNameTextView.setText("Patient: " + patientName);

        // Start AI Scanning Simulation
        startAiScan();

        setupBottomNavigation();
    }

    private void startAiScan() {
        scanLine.setVisibility(View.VISIBLE);
        
        // Animation for scan line moving top to bottom
        ObjectAnimator animator = ObjectAnimator.ofFloat(scanLine, "translationY", 0f, 600f); 
        animator.setDuration(3000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                scanLine.setVisibility(View.GONE);
                statusText.setText("AI Prediction Complete");
                displayUniqueDoseValues();
            }
        });
        animator.start();
    }

    private void displayUniqueDoseValues() {
        // Use patientName hashCode as seed to ensure unique but consistent values for each patient
        long seed = patientName.hashCode();
        Random random = new Random(seed);

        // Generating random but realistic dose values (mGy)
        doseHead.setText(String.format(Locale.getDefault(), "%.2f mGy", 10 + random.nextFloat() * 5));
        doseChest.setText(String.format(Locale.getDefault(), "%.2f mGy", 20 + random.nextFloat() * 10));
        dosePelvis.setText(String.format(Locale.getDefault(), "%.2f mGy", 15 + random.nextFloat() * 8));
        doseAbdomen.setText(String.format(Locale.getDefault(), "%.2f mGy", 18 + random.nextFloat() * 12));
        doseLegs.setText(String.format(Locale.getDefault(), "%.2f mGy", 5 + random.nextFloat() * 5));
        doseArms.setText(String.format(Locale.getDefault(), "%.2f mGy", 4 + random.nextFloat() * 4));

        // Change text color to indicate results are ready
        int resultColor = 0xFFFFFFFF; // White
        doseHead.setTextColor(resultColor);
        doseChest.setTextColor(resultColor);
        dosePelvis.setTextColor(resultColor);
        doseAbdomen.setTextColor(resultColor);
        doseLegs.setTextColor(resultColor);
        doseArms.setTextColor(resultColor);
    }

    private void setupBottomNavigation() {
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
