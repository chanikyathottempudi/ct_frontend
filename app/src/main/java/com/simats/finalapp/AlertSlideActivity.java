package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class AlertSlideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_slide);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlertSlideActivity.this, Dashboard.class);
                startActivity(intent);
            }
        });

        ListView alertsListView = findViewById(R.id.alerts_list_view);

        ArrayList<Alert> alerts = new ArrayList<>();
        alerts.add(new Alert("Patient: Ethan Carter", "Room 201", R.drawable.img_21));
        alerts.add(new Alert("Patient: Olivia Bennett", "Room 305", R.drawable.img_22));
        alerts.add(new Alert("Patient: Noah Thompson", "Room 102", R.drawable.img_23));
        alerts.add(new Alert("Patient: Ava Martinez", "Room 408", R.drawable.img_21));

        AlertAdapter adapter = new AlertAdapter(this, alerts);
        alertsListView.setAdapter(adapter);

        alertsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Alert selectedAlert = alerts.get(position);
                Intent intent = new Intent(AlertSlideActivity.this, PatientAlertSlideActivity.class);
                intent.putExtra("patientName", selectedAlert.getPatientName());
                intent.putExtra("roomNumber", selectedAlert.getRoomNumber());
                intent.putExtra("imageResId", selectedAlert.getImageResId());
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_alerts);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_dashboard) {
                    startActivity(new Intent(AlertSlideActivity.this, Dashboard.class));
                    return true;
                } else if (itemId == R.id.navigation_patients) {
                    startActivity(new Intent(AlertSlideActivity.this, ListOfPatientsActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_scans) {
                    // Assuming you have a ScansActivity
                    // startActivity(new Intent(AlertSlideActivity.this, ScansActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_alerts) {
                    // You are already in AlertSlideActivity, do nothing.
                    return true;
                } else if (itemId == R.id.navigation_admin) {
                    // Assuming you have an AdminActivity
                    // startActivity(new Intent(AlertSlideActivity.this, AdminActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}