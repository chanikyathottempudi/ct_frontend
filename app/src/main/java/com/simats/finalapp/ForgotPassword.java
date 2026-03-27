package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        android.widget.EditText emailInput = findViewById(R.id.email_input);
        Button sendCodeButton = findViewById(R.id.send_code_button);
        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();
                if (android.text.TextUtils.isEmpty(email) || !email.endsWith("@gmail.com")) {
                    Toast.makeText(ForgotPassword.this, "enter correct credential", Toast.LENGTH_SHORT).show();
                    return;
                }

                java.util.Map<String, String> body = new java.util.HashMap<>();
                body.put("email", email);

                com.simats.finalapp.network.RetrofitClient.getApiService().sendVerificationCode(body).enqueue(new retrofit2.Callback<com.simats.finalapp.model.VerificationResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<com.simats.finalapp.model.VerificationResponse> call, retrofit2.Response<com.simats.finalapp.model.VerificationResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(ForgotPassword.this, "Code sent to " + email, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgotPassword.this, VerificationCode.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        } else {
                            String error = "Failed to send code";
                            try {
                                if (response.errorBody() != null) {
                                    error = response.errorBody().string();
                                }
                            } catch (Exception e) {}
                            Toast.makeText(ForgotPassword.this, error, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<com.simats.finalapp.model.VerificationResponse> call, Throwable t) {
                        Toast.makeText(ForgotPassword.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}