package com.simats.finalapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DoseStatisticsPatientDetailsActivity extends AppCompatActivity {

    private LineChart doseTrendChart;
    private PieChart doseDistributionChart;
    private String patientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dose_statistics_patient_details);

        patientName = getIntent().getStringExtra("patientName");
        if (patientName == null) patientName = "Patient";

        TextView patientNameTextView = findViewById(R.id.patient_name_dose_details);
        patientNameTextView.setText(patientName + "'s Dose Statistics");

        findViewById(R.id.back_arrow).setOnClickListener(v -> finish());

        doseTrendChart = findViewById(R.id.dose_trend_chart);
        doseDistributionChart = findViewById(R.id.dose_distribution_chart);

        setupCharts();
        setupBottomNavigation();
    }

    private void setupCharts() {
        long seed = patientName.hashCode();
        Random random = new Random(seed);

        // 1. Setup Line Chart (Dose Trend) - Much easier to understand than bar chart
        List<Entry> entries = new ArrayList<>();
        int totalDlp = 0;
        for (int i = 0; i < 7; i++) {
            int val = 200 + random.nextInt(400);
            entries.add(new Entry(i, val));
            totalDlp += val;
        }

        LineDataSet dataSet = new LineDataSet(entries, "DLP (mGy.cm)");
        dataSet.setColor(Color.parseColor("#0066ff"));
        dataSet.setCircleColor(Color.parseColor("#0066ff"));
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setDrawValues(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData lineData = new LineData(dataSet);
        doseTrendChart.setData(lineData);
        doseTrendChart.getDescription().setEnabled(false);
        doseTrendChart.getXAxis().setDrawGridLines(false);
        doseTrendChart.getAxisRight().setEnabled(false);
        doseTrendChart.animateX(1000);
        doseTrendChart.invalidate();

        // 2. Setup Pie Chart (Organ Distribution) - Clearer for percentage distribution
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(30 + random.nextInt(20), "Head"));
        pieEntries.add(new PieEntry(20 + random.nextInt(20), "Chest"));
        pieEntries.add(new PieEntry(15 + random.nextInt(20), "Abdomen"));
        pieEntries.add(new PieEntry(10 + random.nextInt(10), "Others"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(12f);

        PieData pieData = new PieData(pieDataSet);
        doseDistributionChart.setData(pieData);
        doseDistributionChart.getDescription().setEnabled(false);
        doseDistributionChart.setCenterText("Dose %");
        doseDistributionChart.setHoleRadius(40f);
        doseDistributionChart.animateY(1000);
        doseDistributionChart.invalidate();

        // Update Summary Stats
        TextView totalDlpTv = findViewById(R.id.total_dlp_value);
        totalDlpTv.setText(String.valueOf(totalDlp));
        
        TextView riskTv = findViewById(R.id.avg_risk_value);
        if (totalDlp > 3500) {
            riskTv.setText("High");
            riskTv.setTextColor(Color.RED);
        } else if (totalDlp > 2500) {
            riskTv.setText("Moderate");
            riskTv.setTextColor(Color.parseColor("#F57C00"));
        } else {
            riskTv.setText("Low");
            riskTv.setTextColor(Color.parseColor("#388E3C"));
        }
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
