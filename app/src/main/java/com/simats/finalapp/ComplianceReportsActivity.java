package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ComplianceReportsActivity extends AppCompatActivity {

    private ArrayList<ComplianceRecord> complianceHistory;
    private ComplianceAdapter adapter;
    private boolean isHistoryVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compliance_reports);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> finish());

        TextInputEditText titleInput = findViewById(R.id.compliance_title_input);
        TextInputEditText descInput = findViewById(R.id.compliance_desc_input);
        Button submitButton = findViewById(R.id.submit_compliance_button);
        
        RelativeLayout historyHeader = findViewById(R.id.history_header);
        ListView listView = findViewById(R.id.compliance_list_view);
        ImageView historyArrow = findViewById(R.id.history_arrow);

        // Initializing with clear structured data
        complianceHistory = new ArrayList<>();
        complianceHistory.add(new ComplianceRecord("HIPAA Audit 2023", "PASSED: Full system data encryption verified for all patient records.", "Dec 12, 2023 10:30 AM"));
        complianceHistory.add(new ComplianceRecord("Dose Regulation Report", "COMPLIANT: All CT and MRI scans within safety limits for Q4.", "Jan 05, 2024 02:15 PM"));

        // Use the new custom adapter for clear display
        adapter = new ComplianceAdapter(this, R.layout.list_item_compliance, complianceHistory);
        listView.setAdapter(adapter);

        // Toggle history visibility
        historyHeader.setOnClickListener(v -> {
            isHistoryVisible = !isHistoryVisible;
            if (isHistoryVisible) {
                listView.setVisibility(View.VISIBLE);
                historyArrow.setRotation(90);
            } else {
                listView.setVisibility(View.GONE);
                historyArrow.setRotation(-90);
            }
        });

        submitButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String desc = descInput.getText().toString();

            if (!title.isEmpty() && !desc.isEmpty()) {
                String currentTime = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault()).format(new Date());
                
                // Add new structured record
                ComplianceRecord newRecord = new ComplianceRecord(title, desc, currentTime);
                complianceHistory.add(0, newRecord); 
                adapter.notifyDataSetChanged();
                
                titleInput.setText("");
                descInput.setText("");
                
                // Show history automatically
                listView.setVisibility(View.VISIBLE);
                isHistoryVisible = true;
                historyArrow.setRotation(90);
                
                Toast.makeText(this, "Record added clearly to history!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_admin);
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
                return true;
            }
            return false;
        });
    }
}
