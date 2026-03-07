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

    private TextView timerText;
    private MaterialButton resendButton;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000; // 60 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_code);

        timerText = findViewById(R.id.timer_text);
        resendButton = findViewById(R.id.resend_button);
        ImageView backArrow = findViewById(R.id.back_arrow);
        Button verifyButton = findViewById(R.id.verify_button);

        backArrow.setOnClickListener(v -> finish());

        verifyButton.setOnClickListener(v -> {
            Intent intent = new Intent(VerificationCode.this, SuccessfullyResetPassword.class);
            startActivity(intent);
        });

        resendButton.setOnClickListener(v -> {
            // Logic to resend code
            Toast.makeText(this, "Code Resent!", Toast.LENGTH_SHORT).show();
            resetTimer();
        });

        startTimer();
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
