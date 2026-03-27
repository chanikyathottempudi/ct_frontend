package com.simats.finalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

public class DicomUploadActivity extends AppCompatActivity {

    private ProgressBar uploadProgressBar;
    private TextView progressPercentage;
    private Button saveButton;
    private Button browseButton;
    private int progressStatus = 0;
    private final Handler handler = new Handler();
    private Uri selectedFileUri;

    private String patientName, clinicalIndication, requestingPhysician, scanType;

    private final ActivityResultLauncher<String> filePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedFileUri = uri;
                    Toast.makeText(this, "File Selected", Toast.LENGTH_SHORT).show();
                    startSimulatedUpload();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicom_upload);

        // Get data safely
        Intent intent = getIntent();
        if (intent != null) {
            patientName = intent.getStringExtra("patient_name");
            clinicalIndication = intent.getStringExtra("clinical_indication");
            requestingPhysician = intent.getStringExtra("requesting_physician");
            scanType = intent.getStringExtra("scan_type");
        }

        uploadProgressBar = findViewById(R.id.upload_progress_bar);
        progressPercentage = findViewById(R.id.progress_percentage);
        saveButton = findViewById(R.id.save_button);
        browseButton = findViewById(R.id.browse_button);
        ImageView backArrow = findViewById(R.id.back_arrow);

        if (uploadProgressBar != null) uploadProgressBar.setProgress(0);
        if (progressPercentage != null) progressPercentage.setText("0%");
        if (saveButton != null) saveButton.setEnabled(false);

        if (backArrow != null) {
            backArrow.setOnClickListener(v -> finish());
        }

        if (browseButton != null) {
            browseButton.setOnClickListener(v -> filePickerLauncher.launch("*/*"));
        }

        if (saveButton != null) {
            saveButton.setOnClickListener(v -> {
                if (selectedFileUri == null) {
                    Toast.makeText(this, "Please select a file first", Toast.LENGTH_SHORT).show();
                    return;
                }

                String pId = (intent != null && intent.getStringExtra("patient_id") != null) ? intent.getStringExtra("patient_id") : String.valueOf(100000 + new Random().nextInt(900000));
                String pName = patientName != null ? patientName : "New Patient";
                String notes = "Requesting Physician: " + requestingPhysician;

                com.simats.finalapp.model.PatientRequest request = new com.simats.finalapp.model.PatientRequest(pName, pId, notes, "Unknown");
                
                com.simats.finalapp.network.RetrofitClient.getApiService().registerPatient(request).enqueue(new retrofit2.Callback<com.simats.finalapp.model.PatientResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<com.simats.finalapp.model.PatientResponse> call, retrofit2.Response<com.simats.finalapp.model.PatientResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(DicomUploadActivity.this, "Patient registered successfully in backend!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DicomUploadActivity.this, "Backend sync failed: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                        
                        // Proceed anyway for local UX or handle error strictly
                        Intent listIntent = new Intent(DicomUploadActivity.this, ListOfPatientsActivity.class);
                        listIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(listIntent);
                        finish();
                    }

                    @Override
                    public void onFailure(retrofit2.Call<com.simats.finalapp.model.PatientResponse> call, Throwable t) {
                        Toast.makeText(DicomUploadActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        // Proceed anyway for demo
                        Intent listIntent = new Intent(DicomUploadActivity.this, ListOfPatientsActivity.class);
                        listIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(listIntent);
                        finish();
                    }
                });
            });
        }

        setupBottomNavigation();
    }

    private void startSimulatedUpload() {
        progressStatus = 0;
        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus += 2;
                handler.post(() -> {
                    if (uploadProgressBar != null) uploadProgressBar.setProgress(progressStatus);
                    if (progressPercentage != null) progressPercentage.setText(progressStatus + "%");
                    if (progressStatus >= 100 && saveButton != null) {
                        saveButton.setEnabled(true);
                        saveButton.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_blue_dark));
                    }
                });
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
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
}
