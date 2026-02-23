package com.simats.finalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AlertAdapter extends ArrayAdapter<Alert> {

    public AlertAdapter(@NonNull Context context, ArrayList<Alert> alerts) {
        super(context, 0, alerts);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alert_list_item, parent, false);
        }

        Alert currentAlert = getItem(position);

        TextView patientName = convertView.findViewById(R.id.patient_name);
        TextView roomNumber = convertView.findViewById(R.id.room_number);

        if (currentAlert != null) {
            patientName.setText(currentAlert.getPatientName());
            roomNumber.setText(currentAlert.getRoomNumber());
        }

        return convertView;
    }
}
