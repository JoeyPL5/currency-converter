package com.QueryResponses;
import java.util.Map;

// Represents a response from the API with available currency information. 
public class CurrenciesResponse {
    private Map<String, Double> data;

    public Map<String, Double> getData() {
        return data;
    }

    public void setData(Map<String, Double> data) {
        this.data = data;
    }

    public String[] getCurrencies() {
        return this.getData().keySet().toArray(new String[0]);
    }
}