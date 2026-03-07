package com.simats.finalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ComplianceAdapter extends ArrayAdapter<ComplianceRecord> {
    private Context context;
    private int resource;

    public ComplianceAdapter(@NonNull Context context, int resource, @NonNull List<ComplianceRecord> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        ComplianceRecord record = getItem(position);

        TextView titleView = convertView.findViewById(R.id.compliance_title_text);
        TextView descView = convertView.findViewById(R.id.compliance_desc_text);
        TextView timeView = convertView.findViewById(R.id.compliance_time_text);

        if (record != null) {
            titleView.setText(record.getTitle());
            descView.setText(record.getDescription());
            timeView.setText(record.getTimestamp());
        }

        return convertView;
    }
}
