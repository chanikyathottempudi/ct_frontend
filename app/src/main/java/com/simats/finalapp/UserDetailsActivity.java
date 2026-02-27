package com.simats.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetailsActivity.this, UserManagementActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String role = intent.getStringExtra("role");
        String id = intent.getStringExtra("id");
        int imageResId = intent.getIntExtra("imageResId", R.drawable.ic_profile);

        TextView nameTextView = findViewById(R.id.user_name);
        TextView roleTextView = findViewById(R.id.user_role);
        TextView idTextView = findViewById(R.id.user_id);
        ImageView imageView = findViewById(R.id.user_image);

        nameTextView.setText(name);
        roleTextView.setText(role);
        idTextView.setText(id);
        imageView.setImageResource(imageResId);
    }
}
