package com.simats.finalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.simats.finalapp.Patient;

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

        if (currentPatient != null) {
            patientImage.setImageResource(currentPatient.getImageResId());
            patientName.setText(currentPatient.getName());
            patientId.setText("ID: " + currentPatient.getId());
        }

        return convertView;
    }
}
