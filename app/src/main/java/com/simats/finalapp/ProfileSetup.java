package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileSetup extends AppCompatActivity {

    private EditText fullNameInput, employeeIdInput, departmentInput;
    private int imageResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup);

        ImageView profileImage = findViewById(R.id.profile_image);
        imageResId = getIntent().getIntExtra("imageResId", R.drawable.ic_profile);
        profileImage.setImageResource(imageResId);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileSetup.this, Dashboard.class);
                intent.putExtra("imageResId", imageResId);
                startActivity(intent);
            }
        });

        fullNameInput = findViewById(R.id.full_name_input);
        employeeIdInput = findViewById(R.id.employee_id_input);
        departmentInput = findViewById(R.id.department_input);

        Button saveProfileButton = findViewById(R.id.save_profile_button);
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameInput.getText().toString().trim();
                String employeeId = employeeIdInput.getText().toString().trim();
                String department = departmentInput.getText().toString().trim();

                if (TextUtils.isEmpty(fullName)) {
                    fullNameInput.setError("Full Name is required.");
                    return;
                }

                if (TextUtils.isEmpty(employeeId)) {
                    employeeIdInput.setError("Employee ID is required.");
                    return;
                }

                if (TextUtils.isEmpty(department)) {
                    departmentInput.setError("Department/Role is required.");
                    return;
                }

                Intent intent = new Intent(ProfileSetup.this, Dashboard.class);
                intent.putExtra("imageResId", imageResId);
                startActivity(intent);
            }
        });
    }
}