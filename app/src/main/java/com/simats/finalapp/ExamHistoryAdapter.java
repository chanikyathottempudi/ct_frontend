package com.simats.finalapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExamHistoryAdapter extends RecyclerView.Adapter<ExamHistoryAdapter.ExamViewHolder> {

    private List<Exam> examList;

    public ExamHistoryAdapter(List<Exam> examList) {
        this.examList = examList;
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_history_item, parent, false);
        return new ExamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder, int position) {
        Exam exam = examList.get(position);
        holder.examName.setText(exam.getName());
        holder.examDetails.setText(exam.getDetails());
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }

    static class ExamViewHolder extends RecyclerView.ViewHolder {
        TextView examName, examDetails;

        public ExamViewHolder(@NonNull View itemView) {
            super(itemView);
            examName = itemView.findViewById(R.id.exam_name);
            examDetails = itemView.findViewById(R.id.exam_details);
        }
    }
}