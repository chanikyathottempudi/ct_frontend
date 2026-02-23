package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ExamHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_history);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.exam_history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Exam> examList = new ArrayList<>();
        examList.add(new Exam("CT Head", "2023-08-15 | Facility A | DLP: 1200 mGy*cm"));
        examList.add(new Exam("CT Chest", "2023-07-20 | Facility B | DLP: 1500 mGy*cm"));
        examList.add(new Exam("CT Abdomen", "2023-06-05 | Facility A | DLP: 1800 mGy*cm"));
        examList.add(new Exam("CT Pelvis", "2023-05-10 | Facility C | DLP: 1600 mGy*cm"));
        examList.add(new Exam("CT Spine", "2023-04-01 | Facility B | DLP: 1400 mGy*cm"));

        ExamHistoryAdapter adapter = new ExamHistoryAdapter(examList);
        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_patients);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(ExamHistory.this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(ExamHistory.this, ListOfPatientsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_scans) {
                // Handle scans navigation
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                 startActivity(new Intent(ExamHistory.this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                // Handle admin navigation
                return true;
            }
            return false;
        });
    }
}
