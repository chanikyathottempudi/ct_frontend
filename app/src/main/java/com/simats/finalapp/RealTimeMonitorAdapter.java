package com.simats.finalapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RealTimeMonitorAdapter extends RecyclerView.Adapter<RealTimeMonitorAdapter.ViewHolder> {

    private final List<Patient> patientList;

    public RealTimeMonitorAdapter(List<Patient> patientList) {
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_real_time_monitor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Patient patient = patientList.get(position);
        holder.patientNameTextView.setText(patient.getName());
        holder.patientIdTextView.setText("ID: " + patient.getId());
        holder.patientImageView.setImageResource(patient.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RealTimePatientDetailsActivity.class);
            intent.putExtra("patientName", patient.getName());
            intent.putExtra("patientId", patient.getId());
            intent.putExtra("patientGender", patient.getGender());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView patientImageView;
        TextView patientNameTextView;
        TextView patientIdTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            patientImageView = itemView.findViewById(R.id.patient_image_real_time);
            patientNameTextView = itemView.findViewById(R.id.patient_name_real_time);
            patientIdTextView = itemView.findViewById(R.id.patient_id_real_time);
        }
    }
}
