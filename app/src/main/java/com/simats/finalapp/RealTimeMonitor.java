package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class RealTimeMonitor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.real_time_monitor);

        findViewById(R.id.back_arrow).setOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.real_time_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Patient> patientList = new ArrayList<>();
        patientList.add(new Patient("Ethan Carter", "123456789", "Male", R.drawable.ic_profile));
        patientList.add(new Patient("Sophia Clark", "987654321", "Female", R.drawable.ic_profile));
        patientList.add(new Patient("Liam Davis", "456789123", "Male", R.drawable.ic_profile));
        patientList.add(new Patient("Olivia Evans", "789123456", "Female", R.drawable.ic_profile));
        patientList.add(new Patient("Noah Foster", "321654987", "Male", R.drawable.ic_profile));

        RealTimeMonitorAdapter adapter = new RealTimeMonitorAdapter(patientList);
        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(RealTimeMonitor.this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(RealTimeMonitor.this, ListOfPatientsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_scans) {
                startActivity(new Intent(RealTimeMonitor.this, NewScanRegistrationActivity.class));
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(RealTimeMonitor.this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                // startActivity(new Intent(RealTimeMonitor.this, AdminActivity.class));
                return true;
            }
            return false;
        });
    }
}
