package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SystemLogsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.system_logs);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            Intent intent = new Intent(SystemLogsActivity.this, AdminControlCenterActivity.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.system_logs_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<AuditLog> logList = new ArrayList<>();
        logList.add(new AuditLog("Oct 24, 2023\n10:45:12 AM", "Dr. Smith", "Dose Threshold Changed\nCardiac CT: 450 -> 500mGy"));
        logList.add(new AuditLog("Oct 24, 2023\n09:30:05 AM", "Admin Jones", "Patient Registered\nID: #PX-99281 (Anonymous)"));
        logList.add(new AuditLog("Oct 24, 2023\n08:15:44 AM", "Dr. Smith", "Login\nIP: 192.168.1.104"));
        logList.add(new AuditLog("Oct 23, 2023\n11:55:20 PM", "System", "Automated Alert Dispatch\nHigh Dose Alert - Scanner B"));
        logList.add(new AuditLog("Oct 23, 2023\n10:12:00 PM", "Unknown", "Failed Login Attempt\nUser: support_dev"));

        SystemLogAdapter adapter = new SystemLogAdapter(logList);
        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_admin);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(SystemLogsActivity.this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(SystemLogsActivity.this, ListOfPatientsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_scans) {
                startActivity(new Intent(SystemLogsActivity.this, NewScanRegistrationActivity.class));
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(SystemLogsActivity.this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                startActivity(new Intent(SystemLogsActivity.this, AdminControlCenterActivity.class));
                return true;
            }
            return false;
        });
    }
}
