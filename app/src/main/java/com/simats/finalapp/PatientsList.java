package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PatientsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patients_list);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_patients);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_dashboard) {
                    startActivity(new Intent(PatientsList.this, Dashboard.class));
                    return true;
                } else if (itemId == R.id.navigation_patients) {
                    // You are already in PatientsList, do nothing.
                    return true;
                } else if (itemId == R.id.navigation_scans) {
                    // Assuming you have a ScansActivity
                    // startActivity(new Intent(PatientsList.this, ScansActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_alerts) {
                    // Assuming you have an AlertsActivity
                    // startActivity(new Intent(PatientsList.this, AlertsActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_admin) {
                    // Assuming you have an AdminActivity
                    // startActivity(new Intent(PatientsList.this, AdminActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}