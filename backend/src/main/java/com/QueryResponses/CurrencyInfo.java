package com.QueryResponses;

import com.google.gson.annotations.SerializedName;

// Represents information for a specific currency.
public class CurrencyInfo {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
