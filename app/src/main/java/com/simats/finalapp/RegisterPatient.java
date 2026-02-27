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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_register);

        editTextFullName = findViewById(R.id.edit_text_full_name);
        editTextDob = findViewById(R.id.edit_text_dob);
        spinnerGender = findViewById(R.id.spinner_gender);
        editTextPatientId = findViewById(R.id.edit_text_patient_id);

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

            if (fullName.isEmpty() || patientId.isEmpty()) {
                if (fullName.isEmpty()) editTextFullName.setError("Required");
                if (patientId.isEmpty()) editTextPatientId.setError("Required");
                return;
            }

            int imageResId = "Female".equalsIgnoreCase(gender) ? R.drawable.women_icon : R.drawable.men_icon;
            Patient newPatient = new Patient(fullName, patientId, gender, imageResId);
            PatientManager.getInstance().addPatient(newPatient);

            setResult(RESULT_OK);
            finish();
        });
    }
}
