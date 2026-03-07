package com.simats.finalapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserManagementActivity extends AppCompatActivity {

    private LinearLayout dynamicUsersContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_management);

        dynamicUsersContainer = findViewById(R.id.dynamic_users_container);
        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManagementActivity.this, AdminControlCenterActivity.class);
                startActivity(intent);
            }
        });

        Button createNewUserButton = findViewById(R.id.create_new_user_button);
        createNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManagementActivity.this, NewUserAddingSlideActivity.class);
                startActivity(intent);
            }
        });

        // Set up existing static layouts
        findViewById(R.id.radiologist_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserDetails("Dr. Aris Thorne", "Senior Radiologist", "ID: RAD-204", R.drawable.img_21, null);
            }
        });

        findViewById(R.id.technician_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserDetails("Sarah Jenkins", "Lead Technician", "ID: TEC-882", R.drawable.img_22, null);
            }
        });

        findViewById(R.id.admin_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserDetails("Dr. Elena Rodriguez", "Junior Radiologist", "ID: RAD-312", R.drawable.img_23, null);
            }
        });

        // Check for new user data from Intent
        handleNewUserIntent(getIntent());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_admin);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_dashboard) {
                    startActivity(new Intent(UserManagementActivity.this, Dashboard.class));
                    return true;
                } else if (itemId == R.id.navigation_patients) {
                    startActivity(new Intent(UserManagementActivity.this, PatientList.class));
                    return true;
                } else if (itemId == R.id.navigation_alerts) {
                    startActivity(new Intent(UserManagementActivity.this, PatientAlertSlideActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_admin) {
                    startActivity(new Intent(UserManagementActivity.this, AdminControlCenterActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleNewUserIntent(intent);
    }

    private void handleNewUserIntent(Intent intent) {
        if (intent != null && intent.hasExtra("NEW_USER_NAME")) {
            String name = intent.getStringExtra("NEW_USER_NAME");
            String id = intent.getStringExtra("NEW_USER_ID");
            String role = intent.getStringExtra("NEW_USER_ROLE");
            String imageUriString = intent.getStringExtra("NEW_USER_IMAGE_URI");

            addNewUserView(name, role, id, imageUriString);
        }
    }

    private void addNewUserView(final String name, final String role, final String id, final String imageUriString) {
        View userView = getLayoutInflater().inflate(R.layout.list_item_user_dynamic, null);
        
        CircleImageView imageView = userView.findViewById(R.id.user_image);
        TextView nameText = userView.findViewById(R.id.user_name);
        TextView detailsText = userView.findViewById(R.id.user_details);

        nameText.setText(name);
        detailsText.setText(role + " • ID: " + id);
        
        if (imageUriString != null) {
            imageView.setImageURI(Uri.parse(imageUriString));
        } else {
            imageView.setImageResource(R.drawable.img_21); // Default
        }

        userView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserDetails(name, role, "ID: " + id, 0, imageUriString);
            }
        });

        dynamicUsersContainer.addView(userView);
    }

    private void openUserDetails(String name, String role, String id, int imageResId, String imageUri) {
        Intent intent = new Intent(this, UserDetailsActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("role", role);
        intent.putExtra("id", id);
        if (imageUri != null) {
            intent.putExtra("imageUri", imageUri);
        } else {
            intent.putExtra("imageResId", imageResId);
        }
        startActivity(intent);
    }
}
