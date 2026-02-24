package com.simats.finalapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MonthlyDosePatientDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_dose_patient_details);

        findViewById(R.id.back_arrow).setOnClickListener(v -> finish());

        String patientName = getIntent().getStringExtra("patientName");

        TextView patientNameTextView = findViewById(R.id.patient_name_monthly_dose_details);
        patientNameTextView.setText(patientName + "'s Monthly Dose Trend");

        LineChart lineChart = findViewById(R.id.monthly_dose_line_chart);
        setupLineChart(lineChart);

        BarChart barChart = findViewById(R.id.monthly_heatmap_chart);
        setupBarChart(barChart);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(MonthlyDosePatientDetailsActivity.this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(MonthlyDosePatientDetailsActivity.this, ListOfPatientsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_scans) {
                startActivity(new Intent(MonthlyDosePatientDetailsActivity.this, NewScanRegistrationActivity.class));
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(MonthlyDosePatientDetailsActivity.this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                // startActivity(new Intent(MonthlyDosePatientDetailsActivity.this, AdminActivity.class));
                return true;
            }
            return false;
        });
    }

    private void setupLineChart(LineChart lineChart) {
        List<Entry> entries = new ArrayList<>();
        // Smoother dummy data for a clearer trend
        entries.add(new Entry(0, 50f));
        entries.add(new Entry(1, 80f));
        entries.add(new Entry(2, 60f));
        entries.add(new Entry(3, 90f));
        entries.add(new Entry(4, 110f));
        entries.add(new Entry(5, 100f));
        entries.add(new Entry(6, 130f));
        entries.add(new Entry(7, 150f));
        entries.add(new Entry(8, 120f));
        entries.add(new Entry(9, 140f));
        entries.add(new Entry(10, 170f));
        entries.add(new Entry(11, 160f));

        LineDataSet dataSet = new LineDataSet(entries, "Monthly Dose");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setDrawCircleHole(false);
        dataSet.setLineWidth(2.5f);
        dataSet.setCircleRadius(4f);
        dataSet.setValueTextSize(10f);

        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.BLUE);
        dataSet.setFillAlpha(50);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.DKGRAY);
        xAxis.setDrawGridLines(true); // Enable vertical grid lines
        xAxis.setGridColor(Color.LTGRAY);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                return months[(int) value % months.length];
            }
        });

        lineChart.getAxisLeft().setTextColor(Color.DKGRAY);
        lineChart.getAxisLeft().setDrawGridLines(true); // Enable horizontal grid lines
        lineChart.getAxisLeft().setGridColor(Color.LTGRAY);
        lineChart.getAxisRight().setEnabled(false);

        lineChart.animateX(1000); // Add animation

        lineChart.invalidate();
    }

    private void setupBarChart(BarChart barChart) {
        List<BarEntry> entries = new ArrayList<>();
        // Dummy data for heatmap
        for (int i = 0; i < 12; i++) {
            float value = (float) (Math.random() * 200);
            entries.add(new BarEntry(i, value));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Monthly Heatmap");

        // Color mapping for heatmap effect
        int[] colors = new int[entries.size()];
        for (int i = 0; i < entries.size(); i++) {
            float value = entries.get(i).getY();
            if (value < 50) {
                colors[i] = Color.GREEN;
            } else if (value < 100) {
                colors[i] = Color.YELLOW;
            } else if (value < 150) {
                colors[i] = Color.rgb(255, 165, 0); // Orange
            } else {
                colors[i] = Color.RED;
            }
        }
        dataSet.setColors(colors);

        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(10f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.DKGRAY);
        xAxis.setDrawGridLines(false);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                return months[(int) value % months.length];
            }
        });

        barChart.getAxisLeft().setTextColor(Color.DKGRAY);
        barChart.getAxisRight().setEnabled(false);

        barChart.setFitBars(true);
        barChart.animateY(1000);

        barChart.invalidate();
    }
}
