package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterPatient extends AppCompatActivity {

    private EditText editTextFullName;
    private EditText editTextDob;
    private Spinner spinnerGender;
    private EditText editTextPatientId;
    private EditText editTextAllergies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_register);

        editTextFullName = findViewById(R.id.edit_text_full_name);
        editTextDob = findViewById(R.id.edit_text_dob);
        spinnerGender = findViewById(R.id.spinner_gender);
        editTextPatientId = findViewById(R.id.edit_text_patient_id);
        editTextAllergies = findViewById(R.id.edit_text_allergies);

        findViewById(R.id.close_button).setOnClickListener(v -> finish());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        Button saveAndContinueButton = findViewById(R.id.save_and_continue_button);
        saveAndContinueButton.setOnClickListener(v -> {
            String fullName = editTextFullName.getText().toString();
            String dob = editTextDob.getText().toString();
            String gender = spinnerGender.getSelectedItem().toString();
            String patientId = editTextPatientId.getText().toString();
            String allergies = editTextAllergies.getText().toString();

            if (fullName.isEmpty() || patientId.isEmpty()) {
                if (fullName.isEmpty()) editTextFullName.setError("Required");
                if (patientId.isEmpty()) editTextPatientId.setError("Required");
                return;
            }

            if (allergies.isEmpty()) {
                allergies = "None";
            }

            com.simats.finalapp.model.PatientRequest request = new com.simats.finalapp.model.PatientRequest(fullName, patientId, "Registered via App", gender, dob, allergies);
            
            com.simats.finalapp.network.RetrofitClient.getApiService().registerPatient(request).enqueue(new retrofit2.Callback<com.simats.finalapp.model.PatientResponse>() {
                @Override
                public void onResponse(retrofit2.Call<com.simats.finalapp.model.PatientResponse> call, retrofit2.Response<com.simats.finalapp.model.PatientResponse> response) {
                    if (response.isSuccessful()) {
                        android.widget.Toast.makeText(RegisterPatient.this, "Patient Registered in Backend!", android.widget.Toast.LENGTH_SHORT).show();
                    } else {
                        android.widget.Toast.makeText(RegisterPatient.this, "Sync Failed", android.widget.Toast.LENGTH_SHORT).show();
                    }
                    setResult(RESULT_OK);
                    finish();
                }

                @Override
                public void onFailure(retrofit2.Call<com.simats.finalapp.model.PatientResponse> call, Throwable t) {
                    android.widget.Toast.makeText(RegisterPatient.this, "Network Error", android.widget.Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            });
        });
    }
}
