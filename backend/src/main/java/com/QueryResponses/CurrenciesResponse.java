package com.QueryResponses;
import java.util.Map;

// Represents a response from the API with available currency information. 
public class CurrenciesResponse {
    private Map<String, CurrencyInfo> currencies;

    public Map<String, CurrencyInfo> getCurrencies() {
        return currencies;
    }

    public void setData(Map<String, CurrencyInfo> currencies) {
        this.currencies = currencies;
    }
}