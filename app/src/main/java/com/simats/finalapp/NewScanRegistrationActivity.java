package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NewScanRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_scans_register);

        findViewById(R.id.back_arrow).setOnClickListener(v -> {
            Intent intent = new Intent(NewScanRegistrationActivity.this, Dashboard.class);
            startActivity(intent);
        });

        String[] scanTypes = new String[]{"CT", "MRI", "X-Ray", "Ultrasound"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, scanTypes);
        AutoCompleteTextView scanTypeSpinner = findViewById(R.id.scan_type_spinner);
        scanTypeSpinner.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_scans);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(NewScanRegistrationActivity.this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(NewScanRegistrationActivity.this, ListOfPatientsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_scans) {
                // You are already on this screen
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(NewScanRegistrationActivity.this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                // startActivity(new Intent(NewScanRegistrationActivity.this, AdminActivity.class));
                return true;
            }
            return false;
        });
    }
}