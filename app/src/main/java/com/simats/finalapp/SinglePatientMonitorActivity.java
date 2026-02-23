package com.simats.finalapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SinglePatientMonitorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_monitor);

        findViewById(R.id.back_arrow).setOnClickListener(v -> finish());

        String patientName = getIntent().getStringExtra("patientName");

        TextView patientNameTextView = findViewById(R.id.patient_name_monitor);
        patientNameTextView.setText("Patient: " + patientName);

        LineChart lineChart = findViewById(R.id.patient_line_chart);
        setupLineChart(lineChart);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_patients);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(SinglePatientMonitorActivity.this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(SinglePatientMonitorActivity.this, ListOfPatientsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_scans) {
                startActivity(new Intent(SinglePatientMonitorActivity.this, NewScanRegistrationActivity.class));
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(SinglePatientMonitorActivity.this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                // startActivity(new Intent(SinglePatientMonitorActivity.this, AdminActivity.class));
                return true;
            }
            return false;
        });
    }

    private void setupLineChart(LineChart lineChart) {
        List<Entry> entries = new ArrayList<>();
        // Dummy data for the graph
        for (int i = 0; i < 10; i++) {
            entries.add(new Entry(i, (float) (Math.random() * 100)));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Real-Time Dose");
        dataSet.setColor(Color.WHITE);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setCircleColor(Color.WHITE);
        dataSet.setDrawCircleHole(false);
        dataSet.setLineWidth(2f);
        dataSet.setValueTextSize(10f);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();

        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getAxisRight().setEnabled(false);
    }
}
