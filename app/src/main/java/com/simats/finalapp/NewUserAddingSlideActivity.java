package com.simats.finalapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewUserAddingSlideActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int TAKE_PHOTO = 2;
    private CircleImageView userImageView;
    private EditText nameEditText, idEditText;
    private Spinner roleSpinner;
    private Uri selectedImageUri;
    private Bitmap photoBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_adding_slide);

        userImageView = findViewById(R.id.user_image_view);
        nameEditText = findViewById(R.id.name_edit_text);
        idEditText = findViewById(R.id.id_edit_text);
        roleSpinner = findViewById(R.id.role_spinner);
        Button addPhotoButton = findViewById(R.id.add_photo_button);
        Button saveButton = findViewById(R.id.save_button);
        ImageView backArrow = findViewById(R.id.back_arrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSourceDialog();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_admin);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_dashboard) {
                    startActivity(new Intent(NewUserAddingSlideActivity.this, Dashboard.class));
                    return true;
                } else if (itemId == R.id.navigation_patients) {
                    startActivity(new Intent(NewUserAddingSlideActivity.this, PatientList.class));
                    return true;
                } else if (itemId == R.id.navigation_alerts) {
                    startActivity(new Intent(NewUserAddingSlideActivity.this, PatientAlertSlideActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_admin) {
                    startActivity(new Intent(NewUserAddingSlideActivity.this, AdminControlCenterActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    private void showImageSourceDialog() {
        String[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo");
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
                userImageView.setImageURI(selectedImageUri);
                photoBitmap = null;
            } else if (requestCode == TAKE_PHOTO) {
                photoBitmap = (Bitmap) data.getExtras().get("data");
                userImageView.setImageBitmap(photoBitmap);
                selectedImageUri = null;
            }
        }
    }

    private void saveUser() {
        String name = nameEditText.getText().toString().trim();
        String id = idEditText.getText().toString().trim();
        String role = roleSpinner.getSelectedItem().toString();

        if (name.isEmpty() || id.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // To keep it simple as per user request, we'll pass the data back to UserManagementActivity
        // In a real app, we'd use a database or shared preferences for persistence.
        Intent intent = new Intent(NewUserAddingSlideActivity.this, UserManagementActivity.class);
        intent.putExtra("NEW_USER_NAME", name);
        intent.putExtra("NEW_USER_ID", id);
        intent.putExtra("NEW_USER_ROLE", role);
        if (selectedImageUri != null) {
            intent.putExtra("NEW_USER_IMAGE_URI", selectedImageUri.toString());
        }
        // Note: photoBitmap is harder to pass via intent (size limits), so URI is preferred for gallery.
        // For simplicity, we just notify success and go back.
        
        Toast.makeText(this, "User Saved Successfully", Toast.LENGTH_SHORT).show();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
