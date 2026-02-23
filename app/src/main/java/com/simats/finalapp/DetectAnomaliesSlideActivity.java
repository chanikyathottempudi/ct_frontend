package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DetectAnomaliesSlideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detect_anomalies_slide);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetectAnomaliesSlideActivity.this, Dashboard.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_dashboard) {
                    startActivity(new Intent(DetectAnomaliesSlideActivity.this, Dashboard.class));
                    return true;
                } else if (itemId == R.id.navigation_patients) {
                    startActivity(new Intent(DetectAnomaliesSlideActivity.this, PatientsList.class));
                    return true;
                } else if (itemId == R.id.navigation_scans) {
                    // Assuming you have a ScansActivity
                    // startActivity(new Intent(DetectAnomaliesSlideActivity.this, ScansActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_alerts) {
                    // Assuming you have an AlertsActivity
                    // startActivity(new Intent(DetectAnomaliesSlideActivity.this, AlertsActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_admin) {
                    // Assuming you have an AdminActivity
                    // startActivity(new Intent(DetectAnomaliesSlideActivity.this, AdminActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}