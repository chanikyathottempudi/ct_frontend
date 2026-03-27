package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.simats.finalapp.model.LoginRequest;
import com.simats.finalapp.model.LoginResponse;
import com.simats.finalapp.network.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        TextView signUpText = findViewById(R.id.sign_up_text);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSlide.this, SelectRole.class);
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
                performLogin();
            }
        });
    }

    private void performLogin() {
        String identifier = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (TextUtils.isEmpty(identifier)) {
            emailInput.setError("Email or Employee ID is required.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Password is required.");
            return;
        }

        LoginRequest request = new LoginRequest(identifier, password);
        RetrofitClient.getApiService().login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    // Check for success or presence of access token
                    if (loginResponse.isSuccess() || loginResponse.getAccess() != null) {
                        Toast.makeText(LoginSlide.this, "Welcome!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginSlide.this, Dashboard.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMsg = loginResponse.getError() != null ? loginResponse.getError() : "Invalid credentials";
                        Toast.makeText(LoginSlide.this, errorMsg, Toast.LENGTH_LONG).show();
                    }
                } else {
                    String errorBody = "";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e("LoginError", "Code: " + response.code() + " Error: " + errorBody);
                    Toast.makeText(LoginSlide.this, "Login Failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("LoginError", "Network Failure", t);
                Toast.makeText(LoginSlide.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
