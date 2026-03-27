package com.simats.finalapp.model;

public class PredictDoseResponse {
    private String risk_status;
    private double predicted_dlp;
    private double effective_dose;
    private String protocol_tip;

    public String getRiskStatus() { return risk_status; }
    public double getPredictedDlp() { return predicted_dlp; }
    public double getEffectiveDose() { return effective_dose; }
    public String getProtocolTip() { return protocol_tip; }
}
