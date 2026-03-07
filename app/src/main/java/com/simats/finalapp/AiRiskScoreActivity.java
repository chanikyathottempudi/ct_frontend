package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

public class AiRiskScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_risk_score);

        String tempName = getIntent().getStringExtra("patientName");
        if (tempName == null) tempName = "Selected Patient";
        final String patientName = tempName;

        TextView titleView = findViewById(R.id.title_text_view);
        titleView.setText("AI Risk Score: " + patientName);

        // AI Prediction Logic based on Patient Name (Deterministic Random)
        long seed = patientName.hashCode();
        Random random = new Random(seed);

        // High Risk Metrics
        TextView highRiskValue = findViewById(R.id.high_risk_value);
        TextView highRiskDesc = findViewById(R.id.high_risk_desc);
        int highRiskCount = random.nextInt(20);
        highRiskValue.setText(String.valueOf(highRiskCount));
        highRiskDesc.setText(highRiskCount > 10 ? "Action Required" : "Stable Condition");

        // Pediatric Risk
        TextView pediatricValue = findViewById(R.id.pediatric_risk_value);
        TextView pediatricDesc = findViewById(R.id.pediatric_risk_desc);
        int pediatricCount = random.nextInt(5);
        pediatricValue.setText(String.valueOf(pediatricCount));
        pediatricDesc.setText(pediatricCount > 2 ? "High Priority" : "Normal Protocol");

        // Protocol Deviations
        TextView protocolValue = findViewById(R.id.protocol_deviations_value);
        TextView protocolDesc = findViewById(R.id.protocol_deviations_desc);
        int protocolCount = random.nextInt(10);
        protocolValue.setText(String.valueOf(protocolCount));
        protocolDesc.setText(protocolCount > 5 ? "Anomaly Detected" : "Within Limits");

        findViewById(R.id.back_arrow).setOnClickListener(v -> finish());

        findViewById(R.id.view_detailed_analysis_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, PredictDoseSlideActivity.class);
            intent.putExtra("patientName", patientName);
            startActivity(intent);
        });

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
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
