package com.simats.finalapp;

import java.util.ArrayList;
import java.util.List;

public class AlertManager {
    private static AlertManager instance;
    private final List<Alert> alertList;

    private AlertManager() {
        alertList = new ArrayList<>();
        // Mock alerts - matching the ones in AlertSlideActivity
        alertList.add(new Alert("Patient: Aarav Sharma", "Room 201", R.drawable.men_icon));
        alertList.add(new Alert("Patient: Ishani Gupta", "Room 305", R.drawable.women_icon));
        alertList.add(new Alert("Patient: Vihaan Malhotra", "Room 102", R.drawable.men_icon));
        alertList.add(new Alert("Patient: Diya Iyer", "Room 408", R.drawable.women_icon));
    }

    public static synchronized AlertManager getInstance() {
        if (instance == null) {
            instance = new AlertManager();
        }
        return instance;
    }

    public List<Alert> getAlerts() {
        return alertList;
    }

    public int getAlertCount() {
        return alertList.size();
    }
}
