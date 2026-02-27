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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class RealTimePatientDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_patient_details);

        findViewById(R.id.back_arrow).setOnClickListener(v -> finish());

        TextView patientNameTitle = findViewById(R.id.patient_name_title);
        TextView patientIdDetails = findViewById(R.id.patient_id_details);
        TextView patientGenderDetails = findViewById(R.id.patient_gender_details);

        String patientName = getIntent().getStringExtra("patientName");
        String patientId = getIntent().getStringExtra("patientId");
        String patientGender = getIntent().getStringExtra("patientGender");

        patientNameTitle.setText(patientName);
        patientIdDetails.setText("ID: " + patientId);
        patientGenderDetails.setText("Gender: " + patientGender);

        LineChart chart = findViewById(R.id.real_time_dose_chart);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 1f));
        entries.add(new Entry(1, 2f));
        entries.add(new Entry(2, 3f));
        entries.add(new Entry(3, 4f));
        entries.add(new Entry(4, 5f));
        entries.add(new Entry(5, 4f));
        entries.add(new Entry(6, 3f));

        LineDataSet dataSet = new LineDataSet(entries, "Real-Time Dose");
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setCircleColor(Color.RED);
        dataSet.setCircleHoleColor(Color.WHITE);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh

        chart.getDescription().setEnabled(false);
        chart.getLegend().setTextColor(Color.WHITE);
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getAxisRight().setEnabled(false);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(RealTimePatientDetailsActivity.this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(RealTimePatientDetailsActivity.this, ListOfPatientsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_scans) {
                startActivity(new Intent(RealTimePatientDetailsActivity.this, NewScanRegistrationActivity.class));
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(RealTimePatientDetailsActivity.this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                startActivity(new Intent(RealTimePatientDetailsActivity.this, AdminControlCenterActivity.class));
                return true;
            }
            return false;
        });
    }
}
