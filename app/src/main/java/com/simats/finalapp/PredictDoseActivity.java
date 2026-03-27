package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PredictDoseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.predict_dose);

        final android.widget.EditText bodyPartInput = findViewById(R.id.body_part_input);
        final android.widget.EditText ageInput = findViewById(R.id.age_input);
        final android.widget.EditText weightInput = findViewById(R.id.weight_input);
        final android.widget.EditText kvpInput = findViewById(R.id.kvp_input);
        final android.widget.EditText masInput = findViewById(R.id.mas_input);

        final android.widget.TextView riskStatusText = findViewById(R.id.risk_status_text);
        final android.widget.TextView predictedDlpText = findViewById(R.id.predicted_dlp_text);
        final android.widget.TextView effectiveDoseText = findViewById(R.id.effective_dose_text);
        final android.widget.TextView protocolTipText = findViewById(R.id.protocol_tip_text);

        android.widget.Button generateButton = findViewById(R.id.generate_prediction_button);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bodyPart = bodyPartInput.getText().toString();
                int age = Integer.parseInt(ageInput.getText().toString().isEmpty() ? "0" : ageInput.getText().toString());
                double weight = Double.parseDouble(weightInput.getText().toString().isEmpty() ? "0.0" : weightInput.getText().toString());
                int kvp = Integer.parseInt(kvpInput.getText().toString().isEmpty() ? "120" : kvpInput.getText().toString());
                int mas = Integer.parseInt(masInput.getText().toString().isEmpty() ? "250" : masInput.getText().toString());

                com.simats.finalapp.model.PredictDoseRequest request = new com.simats.finalapp.model.PredictDoseRequest(bodyPart, age, weight, kvp, mas);
                com.simats.finalapp.network.RetrofitClient.getApiService().predictDose(request).enqueue(new retrofit2.Callback<com.simats.finalapp.model.PredictDoseResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<com.simats.finalapp.model.PredictDoseResponse> call, retrofit2.Response<com.simats.finalapp.model.PredictDoseResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            com.simats.finalapp.model.PredictDoseResponse result = response.body();
                            riskStatusText.setText(result.getRiskStatus());
                            predictedDlpText.setText(result.getPredictedDlp() + " mGy.cm");
                            effectiveDoseText.setText(result.getEffectiveDose() + " mSv");
                            protocolTipText.setText(result.getProtocolTip());
                            
                            // Update UI colors based on risk
                            if (result.getRiskStatus().contains("HIGH")) {
                                riskStatusText.setBackgroundColor(android.graphics.Color.parseColor("#FFCDD2"));
                                riskStatusText.setTextColor(android.graphics.Color.parseColor("#D32F2F"));
                            } else {
                                riskStatusText.setBackgroundColor(android.graphics.Color.parseColor("#C8E6C9"));
                                riskStatusText.setTextColor(android.graphics.Color.parseColor("#388E3C"));
                            }
                        } else {
                            android.widget.Toast.makeText(PredictDoseActivity.this, "Prediction failed", android.widget.Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<com.simats.finalapp.model.PredictDoseResponse> call, Throwable t) {
                        android.widget.Toast.makeText(PredictDoseActivity.this, "Network Error: " + t.getMessage(), android.widget.Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_dashboard) {
                    startActivity(new Intent(PredictDoseActivity.this, Dashboard.class));
                    return true;
                } else if (itemId == R.id.navigation_patients) {
                    startActivity(new Intent(PredictDoseActivity.this, PatientsList.class));
                    return true;
                } else if (itemId == R.id.navigation_scans) {
                    // Assuming you have a ScansActivity
                    // startActivity(new Intent(PredictDoseActivity.this, ScansActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_alerts) {
                    // Assuming you have an AlertsActivity
                    // startActivity(new Intent(PredictDoseActivity.this, AlertsActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_admin) {
                    // Assuming you have an AdminActivity
                    // startActivity(new Intent(PredictDoseActivity.this, AdminActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}