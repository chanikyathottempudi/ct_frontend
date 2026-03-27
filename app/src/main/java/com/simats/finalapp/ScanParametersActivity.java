package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ScanParametersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_parameters);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanParametersActivity.this, NewScanRegistrationActivity.class);
                startActivity(intent);
            }
        });

        final android.widget.EditText kvpInput = findViewById(R.id.kvp_input);
        final android.widget.EditText maInput = findViewById(R.id.ma_input);
        final android.widget.EditText pitchInput = findViewById(R.id.pitch_input);
        final android.widget.EditText scanLengthInput = findViewById(R.id.scan_length_input);

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kvp = kvpInput.getText().toString();
                String ma = maInput.getText().toString();
                String pitch = pitchInput.getText().toString();
                String scanLength = scanLengthInput.getText().toString();

                if (android.text.TextUtils.isEmpty(kvp) || android.text.TextUtils.isEmpty(ma)) {
                    android.widget.Toast.makeText(ScanParametersActivity.this, "kVp and mA are required", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }

                com.simats.finalapp.model.ScanParameterRequest request = new com.simats.finalapp.model.ScanParameterRequest(kvp, ma, pitch, scanLength);
                com.simats.finalapp.network.RetrofitClient.getApiService().saveScanParameters(request).enqueue(new retrofit2.Callback<Void>() {
                    @Override
                    public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
                        if (response.isSuccessful()) {
                            android.widget.Toast.makeText(ScanParametersActivity.this, "Scan parameters saved to backend!", android.widget.Toast.LENGTH_SHORT).show();
                        } else {
                            android.widget.Toast.makeText(ScanParametersActivity.this, "Failed to save: " + response.message(), android.widget.Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                        android.widget.Toast.makeText(ScanParametersActivity.this, "Network Error: " + t.getMessage(), android.widget.Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_scans);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(ScanParametersActivity.this, Dashboard.class));
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(ScanParametersActivity.this, ListOfPatientsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_scans) {
                // Already on the Scans flow
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(ScanParametersActivity.this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                startActivity(new Intent(ScanParametersActivity.this, AdminControlCenterActivity.class));
                return true;
            }
            return false;
        });
    }
}
