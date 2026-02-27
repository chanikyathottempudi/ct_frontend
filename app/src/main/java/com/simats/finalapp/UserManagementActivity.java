package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_management);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManagementActivity.this, AdminControlCenterActivity.class);
                startActivity(intent);
            }
        });

        Button createNewUserButton = findViewById(R.id.create_new_user_button);
        createNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManagementActivity.this, NewUserAddingSlideActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout radiologistLayout = findViewById(R.id.radiologist_layout);
        radiologistLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManagementActivity.this, UserDetailsActivity.class);
                intent.putExtra("name", "Dr. Aris Thorne");
                intent.putExtra("role", "Senior Radiologist");
                intent.putExtra("id", "ID: RAD-204");
                intent.putExtra("imageResId", R.drawable.img_21);
                startActivity(intent);
            }
        });

        RelativeLayout technicianLayout = findViewById(R.id.technician_layout);
        technicianLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManagementActivity.this, UserDetailsActivity.class);
                intent.putExtra("name", "Sarah Jenkins");
                intent.putExtra("role", "Lead Technician");
                intent.putExtra("id", "ID: TEC-882");
                intent.putExtra("imageResId", R.drawable.img_22);
                startActivity(intent);
            }
        });

        RelativeLayout adminLayout = findViewById(R.id.admin_layout);
        adminLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManagementActivity.this, UserDetailsActivity.class);
                intent.putExtra("name", "Dr. Elena Rodriguez");
                intent.putExtra("role", "Junior Radiologist");
                intent.putExtra("id", "ID: RAD-312");
                intent.putExtra("imageResId", R.drawable.img_23);
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
                    startActivity(new Intent(UserManagementActivity.this, Dashboard.class));
                    return true;
                } else if (itemId == R.id.navigation_patients) {
                    startActivity(new Intent(UserManagementActivity.this, PatientList.class));
                    return true;
                } else if (itemId == R.id.navigation_scans) {
                    // startActivity(new Intent(UserManagementActivity.this, ScansActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_alerts) {
                    startActivity(new Intent(UserManagementActivity.this, AlertSlideActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_admin) {
                    startActivity(new Intent(UserManagementActivity.this, AdminControlCenterActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}
