package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class NewScanRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_scans_register);

        TextInputEditText patientNameInput = findViewById(R.id.patient_name_input);
        TextInputEditText patientIdInput = findViewById(R.id.patient_id_input);
        TextInputEditText requestingPhysicianInput = findViewById(R.id.requesting_physician_input);
        TextInputEditText scanTypeInput = findViewById(R.id.scan_type_input);

        // Scan type is fixed to CT as per requirement
        scanTypeInput.setText("CT");
        scanTypeInput.setEnabled(false);

        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Moving to DICOM Upload activity
                Intent intent = new Intent(NewScanRegistrationActivity.this, DicomUploadActivity.class);
                intent.putExtra("patient_name", patientNameInput.getText().toString());
                intent.putExtra("patient_id", patientIdInput.getText().toString());
                intent.putExtra("requesting_physician", requestingPhysicianInput.getText().toString());
                intent.putExtra("scan_type", "CT");
                startActivity(intent);
            }
        });

        findViewById(R.id.back_arrow).setOnClickListener(v -> finish());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_scans);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(this, ListOfPatientsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_scans) {
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                startActivity(new Intent(this, AdminControlCenterActivity.class));
                return true;
            }
            return false;
        });
    }
}
