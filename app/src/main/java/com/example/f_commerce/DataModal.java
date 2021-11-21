package com.example.f_commerce;

public class DataModal {


    private String result;
    private float confidence;


    public DataModal(String result, float confidence) {
        this.result = result;
        this.confidence = confidence;
    }

    
    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}