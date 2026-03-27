package com.simats.finalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    private TextView activeAlertsCount;
    private int imageResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        ImageView profileImage = findViewById(R.id.profile_image);
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        imageResId = prefs.getInt("userImageResId", R.drawable.ic_profile);
        profileImage.setImageResource(imageResId);

        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, ProfileSetup.class);
            intent.putExtra("imageResId", imageResId);
            startActivity(intent);
        });

        findViewById(R.id.active_alerts_card).setOnClickListener(v -> {
            startActivity(new Intent(Dashboard.this, AlertSlideActivity.class));
        });

        findViewById(R.id.real_time_monitor_card).setOnClickListener(v -> {
            startActivity(new Intent(Dashboard.this, RealTimeMonitor.class));
        });

        findViewById(R.id.dose_statistics_card).setOnClickListener(v -> {
            startActivity(new Intent(Dashboard.this, DoseStatistics.class));
        });

        findViewById(R.id.daily_dose_trend_card).setOnClickListener(v -> {
            startActivity(new Intent(Dashboard.this, DailyDoseTrend.class));
        });

        findViewById(R.id.monthly_dose_trend_card).setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, ListOfPatientsActivity.class);
            intent.putExtra("FROM_MONTHLY_DOSE", true);
            startActivity(intent);
        });

        activeAlertsCount = findViewById(R.id.active_alerts_count);
        // Corrected: Now showing count of actual alerts instead of all patients
        int alertsCount = AlertManager.getInstance().getAlertCount();
        activeAlertsCount.setText(String.valueOf(alertsCount));

        // AI Safety Intelligence buttons
        findViewById(R.id.ai_risk_score_button).setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, ListOfPatientsActivity.class);
            intent.putExtra("FROM_AI_SAFETY", true);
            intent.putExtra("TARGET_AI_ACTIVITY", "AI_RISK");
            startActivity(intent);
        });

        findViewById(R.id.predict_dose_button).setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, ListOfPatientsActivity.class);
            intent.putExtra("FROM_AI_SAFETY", true);
            intent.putExtra("TARGET_AI_ACTIVITY", "PREDICT_DOSE");
            startActivity(intent);
        });

        findViewById(R.id.detect_anomalies_button).setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, ListOfPatientsActivity.class);
            intent.putExtra("FROM_AI_SAFETY", true);
            intent.putExtra("TARGET_AI_ACTIVITY", "DETECT_ANOMALIES");
            startActivity(intent);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(Dashboard.this, ListOfPatientsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_scans) {
                startActivity(new Intent(Dashboard.this, NewScanRegistrationActivity.class));
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(Dashboard.this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                startActivity(new Intent(Dashboard.this, AdminControlCenterActivity.class));
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh count when returning to dashboard
        if (activeAlertsCount != null) {
            activeAlertsCount.setText(String.valueOf(AlertManager.getInstance().getAlertCount()));
        }
    }
}
