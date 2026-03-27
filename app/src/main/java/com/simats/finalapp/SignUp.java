package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.simats.finalapp.model.SignupRequest;
import com.simats.finalapp.model.SignupResponse;
import com.simats.finalapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    private android.widget.EditText fullNameInput;
    private android.widget.EditText emailInput;
    private android.widget.EditText passwordInput;
    private android.widget.EditText confirmPasswordInput;
    private Spinner roleSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        fullNameInput = findViewById(R.id.full_name_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);
        roleSpinner = findViewById(R.id.role_spinner);
        ImageView backArrow = findViewById(R.id.back_arrow);
        Button createAccountButton = findViewById(R.id.create_account_button);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Setting up the Role Spinner with options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSignup();
            }
        });
    }


    private boolean isValidName(String name) {
        return name.matches("^[a-zA-Z\\s]+$");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$");
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) return false;
        boolean hasUpper = false, hasLower = false, hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
        }
        return hasUpper && hasLower && hasDigit;
    }

    private void performSignup() {
        String fullName = fullNameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();
        String role = roleSpinner.getSelectedItem().toString();

        if (android.text.TextUtils.isEmpty(fullName) || android.text.TextUtils.isEmpty(email) || android.text.TextUtils.isEmpty(password)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidName(fullName) || !isValidEmail(email) || !isValidPassword(password) || !password.equals(confirmPassword)) {
            Toast.makeText(this, "enter correct credential", Toast.LENGTH_SHORT).show();
            return;
        }

        // We use email as the username for backend compatibility
        SignupRequest request = new SignupRequest(fullName, email, email, role, password);
        
        RetrofitClient.getApiService().signup(request).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignUp.this, "Account Created Successfully!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUp.this, LoginSlide.class);
                    startActivity(intent);
                    finish();
                } else {
                    String errorMsg = "Signup Failed";
                    try {
                        if (response.errorBody() != null) {
                            errorMsg += ": " + response.errorBody().string();
                        }
                    } catch (Exception e) {
                        errorMsg += ": " + response.message();
                    }
                    Toast.makeText(SignUp.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(SignUp.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
