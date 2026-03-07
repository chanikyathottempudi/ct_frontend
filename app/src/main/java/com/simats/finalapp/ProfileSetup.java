package com.simats.finalapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileSetup extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int TAKE_PHOTO = 2;
    private CircleImageView profileImage;
    private EditText professionalTitleInput, specializationInput, roleInput;
    private TextView userNameText;
    private int imageResId;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup);

        profileImage = findViewById(R.id.profile_image);
        userNameText = findViewById(R.id.user_name);
        professionalTitleInput = findViewById(R.id.professional_title_input);
        specializationInput = findViewById(R.id.specialization_input);
        roleInput = findViewById(R.id.role_input);

        // Loading default details (simulating data from create account)
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String savedName = prefs.getString("user_full_name", "Dr. Emily Carter");
        String savedRole = prefs.getString("user_role", "Radiologist");
        String savedEmail = prefs.getString("user_email", "emily.carter@hospital.com");

        userNameText.setText(savedName);
        roleInput.setText(savedRole);
        professionalTitleInput.setText("Senior " + savedRole);
        specializationInput.setText("General Medicine");

        imageResId = getIntent().getIntExtra("imageResId", R.drawable.ic_profile);
        profileImage.setImageResource(imageResId);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSourceDialog();
            }
        });

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button completeSetupButton = findViewById(R.id.complete_setup_button);
        completeSetupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileSetup.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileSetup.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileSetup.this, LoginSlide.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void showImageSourceDialog() {
        String[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Profile Picture");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, TAKE_PHOTO);
                } else if (which == 1) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, PICK_IMAGE);
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PICK_IMAGE) {
                selectedImageUri = data.getData();
                profileImage.setImageURI(selectedImageUri);
            } else if (requestCode == TAKE_PHOTO) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                profileImage.setImageBitmap(photo);
            }
        }
    }
}
