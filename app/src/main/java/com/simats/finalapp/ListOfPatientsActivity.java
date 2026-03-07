package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListOfPatientsActivity extends AppCompatActivity {

    private List<Patient> patients;
    private PatientAdapter adapter;
    private ActivityResultLauncher<Intent> registerPatientLauncher;
    private boolean isFromAiSafety = false;
    private String targetAiActivity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_patients);

        isFromAiSafety = getIntent().getBooleanExtra("FROM_AI_SAFETY", false);
        targetAiActivity = getIntent().getStringExtra("TARGET_AI_ACTIVITY");

        TextView headerTitle = findViewById(R.id.header).findViewById(R.id.title_text);
        if (isFromAiSafety && headerTitle != null) {
            headerTitle.setText("Select Patient for AI");
        }

        findViewById(R.id.back_arrow).setOnClickListener(v -> finish());

        ListView patientsListView = findViewById(R.id.patients_list_view);
        patients = PatientManager.getInstance().getPatients();
        adapter = new PatientAdapter(this, (ArrayList<Patient>) patients);
        patientsListView.setAdapter(adapter);

        patientsListView.setOnItemClickListener((parent, view, position, id) -> {
            Patient selectedPatient = patients.get(position);
            
            if (isFromAiSafety) {
                Intent intent;
                if ("AI_RISK".equals(targetAiActivity)) {
                    intent = new Intent(this, AiRiskScoreActivity.class);
                } else if ("PREDICT_DOSE".equals(targetAiActivity)) {
                    intent = new Intent(this, PredictDoseSlideActivity.class);
                } else if ("DETECT_ANOMALIES".equals(targetAiActivity)) {
                    intent = new Intent(this, DetectAnomaliesSlideActivity.class);
                } else {
                    intent = new Intent(this, PatientDetails.class);
                }
                
                intent.putExtra("patientName", selectedPatient.getName());
                intent.putExtra("patientId", selectedPatient.getId());
                intent.putExtra("patientGender", selectedPatient.getGender());
                intent.putExtra("patientImageResId", selectedPatient.getImageResId());
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, PatientDetails.class);
                intent.putExtra("patientName", selectedPatient.getName());
                intent.putExtra("patientId", selectedPatient.getId());
                intent.putExtra("patientGender", selectedPatient.getGender());
                intent.putExtra("patientImageResId", selectedPatient.getImageResId());
                startActivity(intent);
            }
        });

        registerPatientLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        adapter.notifyDataSetChanged();
                    }
                }
        );

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_patients);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
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

        FloatingActionButton fabAddPatient = findViewById(R.id.fab_add_patient);
        fabAddPatient.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterPatient.class);
            registerPatientLauncher.launch(intent);
        });
    }
}
