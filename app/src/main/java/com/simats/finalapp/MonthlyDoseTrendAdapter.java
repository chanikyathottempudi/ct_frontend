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

public class MonthlyDoseTrendAdapter extends RecyclerView.Adapter<MonthlyDoseTrendAdapter.ViewHolder> {

    private final List<Patient> patientList;

    public MonthlyDoseTrendAdapter(List<Patient> patientList) {
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_monthly_dose_trend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Patient patient = patientList.get(position);
        holder.patientNameTextView.setText(patient.getName());
        holder.patientIdTextView.setText("ID: " + patient.getId());

        if ("male".equalsIgnoreCase(patient.getGender())) {
            holder.patientImageView.setImageResource(R.drawable.men_icon);
        } else {
            holder.patientImageView.setImageResource(R.drawable.women_icon);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MonthlyDosePatientDetailsActivity.class);
            intent.putExtra("patientName", patient.getName());
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
            patientImageView = itemView.findViewById(R.id.patient_image_monthly_dose);
            patientNameTextView = itemView.findViewById(R.id.patient_name_monthly_dose);
            patientIdTextView = itemView.findViewById(R.id.patient_id_monthly_dose);
        }
    }
}
