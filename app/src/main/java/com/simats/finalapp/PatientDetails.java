package com.simats.finalapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class PatientDetails extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int CAPTURE_IMAGE = 2;
    private ImageView scanImageView;
    private RecyclerView scansRecyclerView;
    private TextView noHistoryText;
    private ScanAdapter scanAdapter;
    private Patient currentPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_patient);

        findViewById(R.id.back_arrow).setOnClickListener(v -> finish());

        Intent intent = getIntent();
        String patientName = intent.getStringExtra("patientName");
        String patientId = intent.getStringExtra("patientId");
        String patientGender = intent.getStringExtra("patientGender");
        int patientImageResId = intent.getIntExtra("patientImageResId", R.drawable.ic_profile);

        ImageView patientProfileImage = findViewById(R.id.patient_profile_image);
        TextView patientNameTextView = findViewById(R.id.patient_name);
        TextView patientInfoTextView = findViewById(R.id.patient_info);
        TextView patientIdTextView = findViewById(R.id.patient_id);
        scanImageView = findViewById(R.id.scan_image_view);
        scansRecyclerView = findViewById(R.id.scans_recycler_view);
        noHistoryText = findViewById(R.id.no_history_text);

        patientProfileImage.setImageResource(patientImageResId);
        patientNameTextView.setText(patientName);
        patientInfoTextView.setText(patientGender + ", 32");
        patientIdTextView.setText("Patient ID: " + patientId);

        // Find current patient
        List<Patient> patients = PatientManager.getInstance().getPatients();
        for (Patient p : patients) {
            if (p.getId().equals(patientId)) {
                currentPatient = p;
                break;
            }
        }

        if (currentPatient != null) {
            setupScansRecyclerView();
            updateLatestScanDisplay();
        }

        findViewById(R.id.history_button).setOnClickListener(v -> {
            Intent historyIntent = new Intent(PatientDetails.this, ExamHistory.class);
            historyIntent.putExtra("patientName", patientName);
            startActivity(historyIntent);
        });

        findViewById(R.id.graphs_button).setOnClickListener(v -> {
            Intent graphsIntent = new Intent(PatientDetails.this, PatientGraphActivity.class);
            graphsIntent.putExtra("patientName", patientName);
            startActivity(graphsIntent);
        });

        findViewById(R.id.summary_button).setOnClickListener(v -> {
            Intent summaryIntent = new Intent(PatientDetails.this, PatientSummaryActivity.class);
            summaryIntent.putExtra("patientName", patientName);
            startActivity(summaryIntent);
        });

        findViewById(R.id.monitor_button).setOnClickListener(v -> {
            Intent monitorIntent = new Intent(PatientDetails.this, RealTimePatientDetailsActivity.class);
            monitorIntent.putExtra("patientName", patientName);
            monitorIntent.putExtra("patientId", patientId);
            monitorIntent.putExtra("patientGender", patientGender);
            startActivity(monitorIntent);
        });

        findViewById(R.id.upload_scan_button).setOnClickListener(v -> {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, PICK_IMAGE);
        });

        findViewById(R.id.camera_scan_button).setOnClickListener(v -> {
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, CAPTURE_IMAGE);
        });

        setupBottomNavigation();
    }

    private void setupScansRecyclerView() {
        scansRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        scanAdapter = new ScanAdapter(this, currentPatient.getScanUris(), (uri, position) -> {
            currentPatient.removeScanUri(uri);
            scanAdapter.notifyItemRemoved(position);
            updateLatestScanDisplay();
            Toast.makeText(this, "Scan deleted", Toast.LENGTH_SHORT).show();
        });
        scansRecyclerView.setAdapter(scanAdapter);
    }

    private void updateLatestScanDisplay() {
        List<String> scans = currentPatient.getScanUris();
        
        if (scans == null || scans.isEmpty()) {
            // Show "Empty" message and reset the latest scan image
            noHistoryText.setVisibility(View.VISIBLE);
            scansRecyclerView.setVisibility(View.GONE);
            scanImageView.setImageResource(R.drawable.img_12); // Default placeholder
        } else {
            // Hide "Empty" message and show the latest scan
            noHistoryText.setVisibility(View.GONE);
            scansRecyclerView.setVisibility(View.VISIBLE);
            
            String latestUriString = scans.get(scans.size() - 1); // Last one is latest
            if (latestUriString.startsWith("res/")) {
                int resId = getResources().getIdentifier(latestUriString.substring(4), "drawable", getPackageName());
                scanImageView.setImageResource(resId);
            } else {
                scanImageView.setImageURI(Uri.parse(latestUriString));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PICK_IMAGE) {
                Uri selectedImage = data.getData();
                if (currentPatient != null && selectedImage != null) {
                    currentPatient.addScanUri(selectedImage.toString());
                    scanAdapter.notifyDataSetChanged();
                    updateLatestScanDisplay();
                }
            } else if (requestCode == CAPTURE_IMAGE) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if (currentPatient != null && bitmap != null) {
                    String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Scan_" + System.currentTimeMillis(), null);
                    if (path != null) {
                        currentPatient.addScanUri(path);
                        scanAdapter.notifyDataSetChanged();
                        updateLatestScanDisplay();
                    }
                }
            }
        }
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.navigation_patients);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(this, ListOfPatientsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_scans) {
                startActivity(new Intent(this, NewScanRegistrationActivity.class));
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
