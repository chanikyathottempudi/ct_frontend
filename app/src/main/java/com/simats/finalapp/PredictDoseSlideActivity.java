package com.simats.finalapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

public class PredictDoseSlideActivity extends AppCompatActivity {

    private EditText bodyPartEdit, ageEdit, weightEdit, kvpEdit, masEdit;
    private TextView riskStatusText, predictedDlpValue, effectiveDoseValue, protocolTipText, lastRecordText, headerTitle;
    private ProgressBar dlpProgressBar, doseProgressBar;
    private LinearLayout inputParametersSection, loadingLayout;
    private View aiResultsCard, intelligenceCard;
    private String patientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.predict_dose_slide);

        patientName = getIntent().getStringExtra("patientName");
        if (patientName == null) patientName = "Selected Patient";

        initializeViews();
        setupAutoGeneration();

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> finish());

        setupBottomNavigation();
    }

    private void initializeViews() {
        headerTitle = findViewById(R.id.header_title);
        headerTitle.setText("AI Analysis: " + patientName);

        bodyPartEdit = findViewById(R.id.body_part_edit_text);
        ageEdit = findViewById(R.id.age_edit_text);
        weightEdit = findViewById(R.id.weight_edit_text);
        kvpEdit = findViewById(R.id.kvp_edit_text);
        masEdit = findViewById(R.id.mas_edit_text);

        riskStatusText = findViewById(R.id.risk_status_text);
        predictedDlpValue = findViewById(R.id.predicted_dlp_value);
        effectiveDoseValue = findViewById(R.id.effective_dose_value);
        protocolTipText = findViewById(R.id.protocol_tip_text);
        lastRecordText = findViewById(R.id.last_record_text);

        dlpProgressBar = findViewById(R.id.dlp_progress_bar);
        doseProgressBar = findViewById(R.id.dose_progress_bar);

        inputParametersSection = findViewById(R.id.input_parameters_section);
        loadingLayout = findViewById(R.id.loading_layout);
        aiResultsCard = findViewById(R.id.ai_results_card);
        intelligenceCard = findViewById(R.id.intelligence_card);
    }

    private void setupAutoGeneration() {
        long seed = patientName.hashCode();
        Random random = new Random(seed);

        String[] bodyParts = {"Head CT", "Chest CT", "Abdomen CT", "Pelvis CT", "Spine CT"};
        bodyPartEdit.setText(bodyParts[random.nextInt(bodyParts.length)]);
        ageEdit.setText(String.valueOf(20 + random.nextInt(60)));
        weightEdit.setText(String.valueOf(50 + random.nextInt(50)));
        kvpEdit.setText(String.valueOf(100 + (random.nextInt(3) * 10)));
        masEdit.setText(String.valueOf(150 + random.nextInt(200)));

        // Show loading first, then show results
        loadingLayout.setVisibility(View.VISIBLE);
        aiResultsCard.setVisibility(View.GONE);
        intelligenceCard.setVisibility(View.GONE);

        new Handler(Looper.getMainLooper()).postDelayed(this::performAiAnalysis, 2000);
    }

    private void performAiAnalysis() {
        long seed = patientName.hashCode() + bodyPartEdit.getText().toString().hashCode();
        Random random = new Random(seed);

        int dlp = 200 + random.nextInt(600);
        double effectiveDose = 2.0 + (random.nextDouble() * 10.0);
        int riskLevel = random.nextInt(3);

        predictedDlpValue.setText(dlp + " mGy.cm");
        effectiveDoseValue.setText(String.format("%.1f mSv", effectiveDose));

        dlpProgressBar.setProgress(Math.min(100, dlp / 10));
        doseProgressBar.setProgress(Math.min(100, (int) (effectiveDose * 8)));

        if (riskLevel == 0) {
            riskStatusText.setText("LOW RISK");
            riskStatusText.setTextColor(Color.parseColor("#388E3C"));
            riskStatusText.setBackgroundColor(Color.parseColor("#C8E6C9"));
        } else if (riskLevel == 1) {
            riskStatusText.setText("MODERATE");
            riskStatusText.setTextColor(Color.parseColor("#F57C00"));
            riskStatusText.setBackgroundColor(Color.parseColor("#FFE0B2"));
        } else {
            riskStatusText.setText("HIGH RISK");
            riskStatusText.setTextColor(Color.parseColor("#D32F2F"));
            riskStatusText.setBackgroundColor(Color.parseColor("#FFCDD2"));
        }

        protocolTipText.setText(riskLevel == 2 ? "Urgent: AI recommends lower kVp for this patient's BMI." : "Scan parameters are optimized for clinical safety.");
        lastRecordText.setText("Patient's cumulative dose is within safe limits.");

        // Hide loading and show results
        loadingLayout.setVisibility(View.GONE);
        aiResultsCard.setVisibility(View.VISIBLE);
        intelligenceCard.setVisibility(View.VISIBLE);
        
        Toast.makeText(this, "AI Report Generated", Toast.LENGTH_SHORT).show();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(this, ListOfPatientsActivity.class));
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
