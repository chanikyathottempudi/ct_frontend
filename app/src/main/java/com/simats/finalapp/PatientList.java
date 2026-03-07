package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class PatientList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_patients);

        ListView listView = findViewById(R.id.list_view_patients);
        ArrayList<Patient> patients = new ArrayList<>();

        // Adding 13 patients (5 original + 8 new)
        patients.add(new Patient("John Doe", "P001", "Male", R.drawable.men_icon));
        patients.add(new Patient("Jane Smith", "P002", "Female", R.drawable.women_icon));
        patients.add(new Patient("Robert Brown", "P003", "Male", R.drawable.men_icon));
        patients.add(new Patient("Emily Davis", "P004", "Female", R.drawable.women_icon));
        patients.add(new Patient("Michael Wilson", "P005", "Male", R.drawable.men_icon));
        
        // 8 New Patients
        patients.add(new Patient("David Miller", "P006", "Male", R.drawable.men_icon));
        patients.add(new Patient("Sarah Taylor", "P007", "Female", R.drawable.women_icon));
        patients.add(new Patient("James Anderson", "P008", "Male", R.drawable.men_icon));
        patients.add(new Patient("Linda Thomas", "P009", "Female", R.drawable.women_icon));
        patients.add(new Patient("Charles Jackson", "P010", "Male", R.drawable.men_icon));
        patients.add(new Patient("Barbara White", "P011", "Female", R.drawable.women_icon));
        patients.add(new Patient("William Harris", "P012", "Male", R.drawable.men_icon));
        patients.add(new Patient("Susan Martin", "P013", "Female", R.drawable.women_icon));

        PatientAdapter adapter = new PatientAdapter(this, patients);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient selectedPatient = patients.get(position);
                Intent intent = new Intent(PatientList.this, PatientDetails.class);
                intent.putExtra("patient_name", selectedPatient.getName());
                intent.putExtra("patient_id", selectedPatient.getId());
                intent.putExtra("patient_image", selectedPatient.getImageResId());
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_patients);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_dashboard) {
                    startActivity(new Intent(PatientList.this, Dashboard.class));
                    return true;
                } else if (itemId == R.id.navigation_patients) {
                    return true;
                } else if (itemId == R.id.navigation_scans) {
                    // startActivity(new Intent(PatientList.this, ScansActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_alerts) {
                    startActivity(new Intent(PatientList.this, PatientAlertSlideActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_admin) {
                    startActivity(new Intent(PatientList.this, AdminControlCenterActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}
