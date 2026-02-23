package com.simats.finalapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
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

public class PatientGraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_graph);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> finish());

        String patientName = getIntent().getStringExtra("patientName");
        TextView patientNameTextView = findViewById(R.id.patient_name_graph);
        patientNameTextView.setText("Patient: " + patientName);

        LineChart lineChart = findViewById(R.id.line_chart);
        setupLineChart(lineChart);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_patients);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(PatientGraphActivity.this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(PatientGraphActivity.this, ListOfPatientsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_scans) {
                // startActivity(new Intent(PatientGraphActivity.this, ScansActivity.class));
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(PatientGraphActivity.this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                // startActivity(new Intent(PatientGraphActivity.this, AdminActivity.class));
                return true;
            }
            return false;
        });
    }

    private void setupLineChart(LineChart lineChart) {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(2018, 60));
        entries.add(new Entry(2019, 80));
        entries.add(new Entry(2020, 70));
        entries.add(new Entry(2021, 90));
        entries.add(new Entry(2022, 110));
        entries.add(new Entry(2023, 100));
        entries.add(new Entry(2024, 120));

        LineDataSet dataSet = new LineDataSet(entries, "Cumulative Dose");
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
