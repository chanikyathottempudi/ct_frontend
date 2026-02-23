package com.simats.finalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SelectRole extends AppCompatActivity {

    private RelativeLayout radiologistLayout, technicianLayout, adminLayout;
    private String selectedRole = "";
    private int selectedImageResId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_role);

        radiologistLayout = findViewById(R.id.radiologist_layout);
        technicianLayout = findViewById(R.id.technician_layout);
        adminLayout = findViewById(R.id.admin_layout);
        Button continueButton = findViewById(R.id.continue_button);
        ImageView backArrow = findViewById(R.id.back_arrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRole.this, SecondSlide.class);
                startActivity(intent);
            }
        });

        radiologistLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRole("radiologist", R.drawable.img_21);
            }
        });

        technicianLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRole("technician", R.drawable.img_22);
            }
        });

        adminLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRole("admin", R.drawable.img_23);
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedRole.isEmpty()) {
                    SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("userImageResId", selectedImageResId);
                    editor.apply();

                    Intent intent = new Intent(SelectRole.this, LoginSlide.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void selectRole(String role, int imageResId) {
        selectedRole = role;
        selectedImageResId = imageResId;
        radiologistLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.role_unselected_background));
        technicianLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.role_unselected_background));
        adminLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.role_unselected_background));

        if (role.equals("radiologist")) {
            radiologistLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.role_selected_background));
        } else if (role.equals("technician")) {
            technicianLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.role_selected_background));
        } else if (role.equals("admin")) {
            adminLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.role_selected_background));
        }
    }
}