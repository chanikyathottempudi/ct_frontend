package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginSlide extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_slide);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        Button secureLoginButton = findViewById(R.id.secure_login_button);
        ImageView backArrow = findViewById(R.id.back_arrow);
        TextView forgotPasswordText = findViewById(R.id.forgot_password_text);
        TextView signUpText = findViewById(R.id.sign_up_text);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSlide.this, SelectRole.class);
                startActivity(intent);
            }
        });

        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSlide.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSlide.this, SignUp.class);
                startActivity(intent);
            }
        });

        secureLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailInput.setError("Email or Employee ID is required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordInput.setError("Password is required.");
                    return;
                }

                Intent intent = new Intent(LoginSlide.this, Dashboard.class);
                startActivity(intent);
            }
        });
    }
}