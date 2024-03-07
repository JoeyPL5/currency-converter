package com;
import java.util.Map;

// Represents a response from the API with exchange rates information. 
public class ExchangeRatesResponse {
    private Map<String, Double> data;

    public Map<String, Double> getData() {
        return data;
    }

    public void setData(Map<String, Double> data) {
        this.data = data;
    }
}