package com.simats.finalapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SystemLogAdapter extends RecyclerView.Adapter<SystemLogAdapter.ViewHolder> {

    private final List<AuditLog> logList;

    public SystemLogAdapter(List<AuditLog> logList) {
        this.logList = logList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_system_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AuditLog log = logList.get(position);
        holder.timestampTextView.setText(log.getTimestamp());
        holder.userTextView.setText(log.getUser());
        holder.actionTextView.setText(log.getAction());
    }

    @Override
    public int getItemCount() {
        return logList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView timestampTextView;
        TextView userTextView;
        TextView actionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timestampTextView = itemView.findViewById(R.id.timestamp_text_view);
            userTextView = itemView.findViewById(R.id.user_text_view);
            actionTextView = itemView.findViewById(R.id.action_text_view);
        }
    }
}
