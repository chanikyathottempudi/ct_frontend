package com.simats.finalapp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientAdapter extends ArrayAdapter<Patient> {

    public PatientAdapter(@NonNull Context context, ArrayList<Patient> patients) {
        super(context, 0, patients);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_patient, parent, false);
        }

        Patient currentPatient = getItem(position);

        CircleImageView patientImage = convertView.findViewById(R.id.patient_image);
        TextView patientName = convertView.findViewById(R.id.patient_name);
        TextView patientId = convertView.findViewById(R.id.patient_id);
        ImageView deleteButton = convertView.findViewById(R.id.delete_patient_button);

        if (currentPatient != null) {
            patientImage.setImageResource(currentPatient.getImageResId());
            patientName.setText(currentPatient.getName());
            patientId.setText("ID: " + currentPatient.getId());

            deleteButton.setOnClickListener(v -> {
                showDeleteConfirmation(currentPatient, position);
            });
        }

        return convertView;
    }

    private void showDeleteConfirmation(Patient patient, int position) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Patient")
                .setMessage("Are you sure you want to delete " + patient.getName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    remove(patient);
                    PatientManager.getInstance().getPatients().remove(patient);
                    notifyDataSetChanged();
                    Toast.makeText(getContext(), patient.getName() + " deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
