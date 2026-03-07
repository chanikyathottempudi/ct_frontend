package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SuccessfullyResetPassword extends AppCompatActivity {

    private TextInputEditText newPasswordInput, repeatPasswordInput;
    private TextView strengthText;
    private View strengthBar1, strengthBar2, strengthBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.successfully_reset_password);

        newPasswordInput = findViewById(R.id.new_password_input);
        repeatPasswordInput = findViewById(R.id.repeat_password_input);
        strengthText = findViewById(R.id.strength_text);
        strengthBar1 = findViewById(R.id.strength_bar_1);
        strengthBar2 = findViewById(R.id.strength_bar_2);
        strengthBar3 = findViewById(R.id.strength_bar_3);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> finish());

        newPasswordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateStrengthIndicator(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        Button saveButton = findViewById(R.id.save_password_button);
        saveButton.setOnClickListener(v -> {
            String pass = newPasswordInput.getText().toString();
            String repeatPass = repeatPasswordInput.getText().toString();

            if (pass.isEmpty() || repeatPass.isEmpty()) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pass.equals(repeatPass)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Account Secured Successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SuccessfullyResetPassword.this, LoginSlide.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void updateStrengthIndicator(String password) {
        if (password.length() == 0) {
            strengthText.setText("Security Strength: None");
            setBarsColor(0x33ffffff, 0x33ffffff, 0x33ffffff);
        } else if (password.length() < 6) {
            strengthText.setText("Security Strength: Weak");
            strengthText.setTextColor(0xffff4444); // Red
            setBarsColor(0xffff4444, 0x33ffffff, 0x33ffffff);
        } else if (password.length() < 10) {
            strengthText.setText("Security Strength: Medium");
            strengthText.setTextColor(0xffffbb33); // Orange/Yellow
            setBarsColor(0xffffbb33, 0xffffbb33, 0x33ffffff);
        } else {
            strengthText.setText("Security Strength: Strong");
            strengthText.setTextColor(0xff00c851); // Green
            setBarsColor(0xff00c851, 0xff00c851, 0xff00c851);
        }
    }

    private void setBarsColor(int c1, int c2, int c3) {
        strengthBar1.setBackgroundColor(c1);
        strengthBar2.setBackgroundColor(c2);
        strengthBar3.setBackgroundColor(c3);
    }
}
