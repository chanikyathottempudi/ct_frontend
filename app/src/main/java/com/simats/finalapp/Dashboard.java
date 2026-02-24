package com.simats.finalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.simats.finalapp.Patient;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    private TextView activeAlertsCount;
    private final ArrayList<Patient> patientList = new ArrayList<>(); // In a real app, this would be managed elsewhere
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

        CardView activeAlertsCard = findViewById(R.id.active_alerts_card);
        activeAlertsCard.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, ListOfPatientsActivity.class);
            startActivity(intent);
        });

        CardView realTimeMonitorCard = findViewById(R.id.real_time_monitor_card);
        realTimeMonitorCard.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, RealTimeMonitor.class);
            startActivity(intent);
        });

        CardView doseStatisticsCard = findViewById(R.id.dose_statistics_card);
        doseStatisticsCard.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, DoseStatistics.class);
            startActivity(intent);
        });

        CardView dailyDoseTrendCard = findViewById(R.id.daily_dose_trend_card);
        dailyDoseTrendCard.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, DailyDoseTrend.class);
            startActivity(intent);
        });

        CardView monthlyDoseTrendCard = findViewById(R.id.monthly_dose_trend_card);
        monthlyDoseTrendCard.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, MonthlyDoseTrend.class);
            startActivity(intent);
        });

        activeAlertsCount = findViewById(R.id.active_alerts_count);

        // Dummy data for patients, in a real app this would come from a database or API
        patientList.add(new Patient("Ethan Carter", "123456789", "male", R.drawable.ic_profile));
        patientList.add(new Patient("Sophia Clark", "987654321", "female", R.drawable.ic_profile));
        patientList.add(new Patient("Liam Davis", "456789123", "male", R.drawable.ic_profile));
        patientList.add(new Patient("Olivia Evans", "789123456", "female", R.drawable.ic_profile));
        patientList.add(new Patient("Noah Foster", "321654987", "male", R.drawable.ic_profile));

        updateActiveAlertsCount();

        Button aiRiskScoreButton = findViewById(R.id.ai_risk_score_button);
        aiRiskScoreButton.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, AiRiskScoreActivity.class);
            startActivity(intent);
        });

        Button predictDoseButton = findViewById(R.id.predict_dose_button);
        predictDoseButton.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, PredictDoseSlideActivity.class);
            startActivity(intent);
        });

        Button detectAnomaliesButton = findViewById(R.id.detect_anomalies_button);
        detectAnomaliesButton.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, DetectAnomaliesSlideActivity.class);
            startActivity(intent);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                // You are already in Dashboard, do nothing.
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
            }
            // Assuming you have an AdminActivity
            // startActivity(new Intent(Dashboard.this, AdminActivity.class));
            return itemId == R.id.navigation_admin;
        });
    }

    private void updateActiveAlertsCount() {
        activeAlertsCount.setText(String.valueOf(patientList.size()));
    }
}
