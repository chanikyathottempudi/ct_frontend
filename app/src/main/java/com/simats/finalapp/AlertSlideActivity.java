package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
        alerts.add(new Alert("Patient: Aarav Sharma", "Room 201", R.drawable.men_icon));
        alerts.add(new Alert("Patient: Ishani Gupta", "Room 305", R.drawable.women_icon));
        alerts.add(new Alert("Patient: Vihaan Malhotra", "Room 102", R.drawable.men_icon));
        alerts.add(new Alert("Patient: Diya Iyer", "Room 408", R.drawable.women_icon));

        // Update the count of active alerts in the UI
        TextView alertCountText = findViewById(R.id.active_alerts_count_text);
        if (alertCountText != null) {
            alertCountText.setText(alerts.size() + " Active Alerts");
        }

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

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
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
                    startActivity(new Intent(AlertSlideActivity.this, NewScanRegistrationActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_alerts) {
                    return true;
                } else if (itemId == R.id.navigation_admin) {
                    startActivity(new Intent(AlertSlideActivity.this, AdminControlCenterActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}
