package com.simats.finalapp.model;

public class ScanParameterRequest {
    private String kvp;
    private String ma;
    private String pitch;
    private String scan_length;

    public ScanParameterRequest(String kvp, String ma, String pitch, String scan_length) {
        this.kvp = kvp;
        this.ma = ma;
        this.pitch = pitch;
        this.scan_length = scan_length;
    }
}
