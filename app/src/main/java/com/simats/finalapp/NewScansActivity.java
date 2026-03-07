package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NewScansActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_scans);

        Intent intent = getIntent();
        String patientName = intent.getStringExtra("patient_name");
        String clinicalIndication = intent.getStringExtra("clinical_indication");
        String requestingPhysician = intent.getStringExtra("requesting_physician");
        String scanType = intent.getStringExtra("scan_type");

        TextView patientNameText = findViewById(R.id.patient_name_text);
        TextView clinicalIndicationText = findViewById(R.id.clinical_indication_text);
        TextView requestingPhysicianText = findViewById(R.id.requesting_physician_text);
        TextView scanTypeText = findViewById(R.id.scan_type_text);

        patientNameText.setText("Patient Name: " + patientName);
        clinicalIndicationText.setText("Clinical Indication: " + clinicalIndication);
        requestingPhysicianText.setText("Requesting Physician: " + requestingPhysician);
        scanTypeText.setText("Scan Type: " + scanType);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewScansActivity.this, NewScanRegistrationActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_scans);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(NewScansActivity.this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(NewScansActivity.this, ListOfPatientsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_scans) {
                // Already on the Scans flow
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(NewScansActivity.this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                startActivity(new Intent(NewScansActivity.this, AdminControlCenterActivity.class));
                return true;
            }
            return false;
        });
    }
}
