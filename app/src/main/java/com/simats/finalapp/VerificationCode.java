package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class VerificationCode extends AppCompatActivity {

    private android.widget.EditText code1, code2, code3, code4, code5, code6;
    private android.widget.TextView timerText;
    private com.google.android.material.button.MaterialButton resendButton;
    private android.os.CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000; // 60 seconds
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_code);

        email = getIntent().getStringExtra("email");

        code1 = findViewById(R.id.code_1);
        code2 = findViewById(R.id.code_2);
        code3 = findViewById(R.id.code_3);
        code4 = findViewById(R.id.code_4);
        code5 = findViewById(R.id.code_5);
        code6 = findViewById(R.id.code_6);

        setupInputField(code1, code2);
        setupInputField(code2, code3);
        setupInputField(code3, code4);
        setupInputField(code4, code5);
        setupInputField(code5, code6);
        setupInputField(code6, null);

        timerText = findViewById(R.id.timer_text);
        resendButton = findViewById(R.id.resend_button);
        ImageView backArrow = findViewById(R.id.back_arrow);
        Button verifyButton = findViewById(R.id.verify_button);

        backArrow.setOnClickListener(v -> finish());

        verifyButton.setOnClickListener(v -> {
            String code = code1.getText().toString() + code2.getText().toString() +
                          code3.getText().toString() + code4.getText().toString() +
                          code5.getText().toString() + code6.getText().toString();

            if (code.length() < 6) {
                Toast.makeText(this, "Please enter 6-digit code", Toast.LENGTH_SHORT).show();
                return;
            }

            verify(code);
        });

        resendButton.setOnClickListener(v -> {
            // Logic to resend code
            resendCode();
        });

        startTimer();
    }

    private void setupInputField(android.widget.EditText current, android.widget.EditText next) {
        current.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1 && next != null) {
                    next.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    private void resendCode() {
        if (email == null) return;
        java.util.Map<String, String> body = new java.util.HashMap<>();
        body.put("email", email);
        com.simats.finalapp.network.RetrofitClient.getApiService().sendVerificationCode(body).enqueue(new retrofit2.Callback<com.simats.finalapp.model.VerificationResponse>() {
            @Override
            public void onResponse(retrofit2.Call<com.simats.finalapp.model.VerificationResponse> call, retrofit2.Response<com.simats.finalapp.model.VerificationResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(VerificationCode.this, "Code Resent!", Toast.LENGTH_SHORT).show();
                    resetTimer();
                } else {
                    Toast.makeText(VerificationCode.this, "Failed to resend code", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<com.simats.finalapp.model.VerificationResponse> call, Throwable t) {
                Toast.makeText(VerificationCode.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verify(String code) {
        com.simats.finalapp.model.VerificationRequest request = new com.simats.finalapp.model.VerificationRequest(email, code);
        com.simats.finalapp.network.RetrofitClient.getApiService().verifyCode(request).enqueue(new retrofit2.Callback<com.simats.finalapp.model.VerificationResponse>() {
            @Override
            public void onResponse(retrofit2.Call<com.simats.finalapp.model.VerificationResponse> call, retrofit2.Response<com.simats.finalapp.model.VerificationResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(VerificationCode.this, "Verified Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VerificationCode.this, SuccessfullyResetPassword.class);
                    intent.putExtra("email", email);
                    intent.putExtra("code", code);
                    startActivity(intent);
                } else {
                    Toast.makeText(VerificationCode.this, "Invalid Code", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<com.simats.finalapp.model.VerificationResponse> call, Throwable t) {
                Toast.makeText(VerificationCode.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                timerText.setText("Code expired");
                resendButton.setEnabled(true);
                resendButton.setTextColor(getResources().getColor(android.R.color.white));
            }
        }.start();
    }

    private void resetTimer() {
        timeLeftInMillis = 60000;
        resendButton.setEnabled(false);
        resendButton.setTextColor(getResources().getColor(android.R.color.darker_gray));
        startTimer();
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "Resend code in %02d:%02d", minutes, seconds);
        timerText.setText(timeFormatted);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
