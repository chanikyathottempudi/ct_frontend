package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminControlCenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_control_center);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControlCenterActivity.this, Dashboard.class);
                startActivity(intent);
            }
        });

        RelativeLayout userManagementLayout = findViewById(R.id.user_management_layout);
        userManagementLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControlCenterActivity.this, UserManagementActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout securityPrivacyLayout = findViewById(R.id.security_privacy_layout);
        securityPrivacyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControlCenterActivity.this, SecurityAndPrivacyActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout machineIntegrationLayout = findViewById(R.id.machine_integration_layout);
        machineIntegrationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControlCenterActivity.this, MachineIntegrationActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout complianceReportsLayout = findViewById(R.id.compliance_reports_layout);
        complianceReportsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControlCenterActivity.this, ComplianceReportsActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout systemLogsLayout = findViewById(R.id.system_logs_layout);
        systemLogsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControlCenterActivity.this, SystemLogsActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_admin);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_dashboard) {
                    startActivity(new Intent(AdminControlCenterActivity.this, Dashboard.class));
                    return true;
                } else if (itemId == R.id.navigation_patients) {
                    startActivity(new Intent(AdminControlCenterActivity.this, PatientList.class));
                    return true;
                } else if (itemId == R.id.navigation_scans) {
                    startActivity(new Intent(AdminControlCenterActivity.this, NewScanRegistrationActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_alerts) {
                    startActivity(new Intent(AdminControlCenterActivity.this, AlertSlideActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_admin) {
                    return true;
                }
                return false;
            }
        });
    }
}
