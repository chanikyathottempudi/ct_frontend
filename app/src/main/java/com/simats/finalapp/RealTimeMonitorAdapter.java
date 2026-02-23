package com.simats.finalapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

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
        holder.patientNameTextView.setText("Patient: " + patient.getName());
        setupLineChart(holder.lineChart);
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView patientNameTextView;
        LineChart lineChart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            patientNameTextView = itemView.findViewById(R.id.patient_name_monitor);
            lineChart = itemView.findViewById(R.id.patient_line_chart);
        }
    }

    private void setupLineChart(LineChart lineChart) {
        List<Entry> entries = new ArrayList<>();
        // Dummy data for the graph
        for (int i = 0; i < 10; i++) {
            entries.add(new Entry(i, (float) (Math.random() * 100)));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Real-Time Dose");
        dataSet.setColor(Color.WHITE);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setCircleColor(Color.WHITE);
        dataSet.setDrawCircleHole(false);
        dataSet.setLineWidth(2f);
        dataSet.setValueTextSize(10f);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();

        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getAxisRight().setEnabled(false);
    }
}
