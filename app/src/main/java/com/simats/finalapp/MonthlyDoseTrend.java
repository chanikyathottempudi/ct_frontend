package com.simats.finalapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MonthlyDoseTrend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthly_dose_trend);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> finish());

        TextView currentMonthDose = findViewById(R.id.current_month_dose);
        TextView previousMonthDose = findViewById(R.id.previous_month_dose);
        BarChart barChart = findViewById(R.id.monthly_bar_chart);

        currentMonthDose.setText("420 mSv");
        previousMonthDose.setText("380 mSv");

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 350));
        entries.add(new BarEntry(1, 400));
        entries.add(new BarEntry(2, 380));
        entries.add(new BarEntry(3, 420));

        BarDataSet dataSet = new BarDataSet(entries, "Monthly Dose (mSv)");
        dataSet.setColor(Color.parseColor("#0066ff"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        final String[] months = {"Oct", "Nov", "Dec", "Jan"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < months.length) {
                    return months[index];
                }
                return "";
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(false);

        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000);
        barChart.invalidate();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                return true;
            } else if (itemId == R.id.navigation_patients) {
                startActivity(new Intent(this, ListOfPatientsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_alerts) {
                startActivity(new Intent(this, AlertSlideActivity.class));
                return true;
            } else if (itemId == R.id.navigation_admin) {
                startActivity(new Intent(this, AdminControlCenterActivity.class));
                return true;
            }
            return false;
        });
    }
}
