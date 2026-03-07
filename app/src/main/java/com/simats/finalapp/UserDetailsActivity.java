package com.simats.finalapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailsActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int CAPTURE_IMAGE = 2;
    private CircleImageView userImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userImageView = findViewById(R.id.user_image);
        TextView nameTextView = findViewById(R.id.user_name);
        TextView roleTextView = findViewById(R.id.user_role);
        TextView idTextView = findViewById(R.id.user_id);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String role = intent.getStringExtra("role");
        String id = intent.getStringExtra("id");
        int imageResId = intent.getIntExtra("imageResId", R.drawable.ic_profile);
        String imageUriString = intent.getStringExtra("imageUri");

        nameTextView.setText(name != null ? name : "Unknown User");
        roleTextView.setText(role != null ? role : "Staff");
        idTextView.setText(id != null ? id : "ID: ---");

        if (imageUriString != null) {
            userImageView.setImageURI(Uri.parse(imageUriString));
        } else {
            userImageView.setImageResource(imageResId);
        }

        // Click on image to change profile photo
        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSourceDialog();
            }
        });
    }

    private void showImageSourceDialog() {
        String[] options = {"Take Photo", "Choose from Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Profile Picture");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, CAPTURE_IMAGE);
            } else {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, PICK_IMAGE);
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PICK_IMAGE) {
                Uri selectedImage = data.getData();
                userImageView.setImageURI(selectedImage);
                Toast.makeText(this, "Profile picture updated from Gallery", Toast.LENGTH_SHORT).show();
            } else if (requestCode == CAPTURE_IMAGE) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                userImageView.setImageBitmap(bitmap);
                Toast.makeText(this, "Profile picture updated from Camera", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
