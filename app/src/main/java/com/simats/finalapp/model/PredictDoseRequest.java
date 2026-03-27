package com.simats.finalapp.model;

public class PredictDoseRequest {
    private String body_part;
    private int age;
    private double weight;
    private int kvp;
    private int mas;

    public PredictDoseRequest(String body_part, int age, double weight, int kvp, int mas) {
        this.body_part = body_part;
        this.age = age;
        this.weight = weight;
        this.kvp = kvp;
        this.mas = mas;
    }
}
