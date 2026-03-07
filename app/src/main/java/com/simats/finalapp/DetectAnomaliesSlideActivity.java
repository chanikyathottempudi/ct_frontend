package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

public class DetectAnomaliesSlideActivity extends AppCompatActivity {

    private String patientName;
    private LinearLayout anomaliesContainer;
    private TextView headerTitle, avgDlpValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detect_anomalies_slide);

        patientName = getIntent().getStringExtra("patientName");
        if (patientName == null) patientName = "Selected Patient";

        headerTitle = findViewById(R.id.header_title);
        avgDlpValue = findViewById(R.id.avg_dlp_value);
        anomaliesContainer = findViewById(R.id.anomalies_container);

        headerTitle.setText("Anomalies: " + patientName);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            // Since we came from ListOfPatientsActivity, finish() will take us back there
            finish();
        });

        generateDynamicAnomalies();
        setupBottomNavigation();
    }

    private void generateDynamicAnomalies() {
        // Use patient name hash to make results deterministic but different for each patient
        long seed = patientName.hashCode();
        Random random = new Random(seed);

        int baseDlp = 300 + random.nextInt(300);
        avgDlpValue.setText(baseDlp + " MGY.CM (AVG)");

        // Clear previous anomalies if any
        anomaliesContainer.removeAllViews();

        int anomalyCount = 1 + random.nextInt(3); // 1 to 3 anomalies

        String[] types = {"Chest CT", "Head CT", "Abdomen CT", "Pelvis CT", "Spine CT"};
        String[] issues = {"Unexpected Dose Spike", "Protocol Mismatch", "Multiple Repeated Scans", "Incorrect Patient Positioning", "Scanner Calibration Error"};
        String[] levels = {"CRITICAL", "WARNING", "INFO"};

        for (int i = 0; i < anomalyCount; i++) {
            addAnomalyCard(
                    "#PX-" + (100 + random.nextInt(900)) + " | " + types[random.nextInt(types.length)],
                    issues[random.nextInt(issues.length)],
                    "DLP: " + (baseDlp + random.nextInt(200)) + " mGy.cm",
                    levels[random.nextInt(levels.length)]
            );
        }
    }

    private void addAnomalyCard(String idText, String issueText, String dlpText, String levelText) {
        View cardView = LayoutInflater.from(this).inflate(R.layout.anomaly_item_template, anomaliesContainer, false);

        TextView idTv = cardView.findViewById(R.id.anomaly_id);
        TextView issueTv = cardView.findViewById(R.id.anomaly_issue);
        TextView dlpTv = cardView.findViewById(R.id.anomaly_dlp);
        TextView levelTv = cardView.findViewById(R.id.anomaly_level);
        View rootLayout = cardView.findViewById(R.id.anomaly_root_layout);

        idTv.setText(idText);
        issueTv.setText(issueText);
        dlpTv.setText(dlpText);
        levelTv.setText(levelText);

        // Styling based on level
        if ("CRITICAL".equals(levelText)) {
            rootLayout.setBackgroundColor(0xFFFFF0F0); // Light red
            levelTv.setBackgroundColor(0xFFFFCDD2);
            levelTv.setTextColor(0xFFD32F2F);
            issueTv.setTextColor(0xFFD32F2F);
        } else if ("WARNING".equals(levelText)) {
            rootLayout.setBackgroundColor(0xFFFFF9E6); // Light orange/yellow
            levelTv.setBackgroundColor(0xFFFFF9C4);
            levelTv.setTextColor(0xFFFBC02D);
        } else {
            rootLayout.setBackgroundColor(0xFFF0F4F8); // Light blue/grey
            levelTv.setBackgroundColor(0xFFE3F2FD);
            levelTv.setTextColor(0xFF0066FF);
        }

        anomaliesContainer.addView(cardView);
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
