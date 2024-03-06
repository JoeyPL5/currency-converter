package com;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CurrencyConversionService {
    @Value("${exchange-rates-api.key}")
    private String apiKey;

    @Value("${exchange-rates-api.base}")
    private String apiBase;

    private final RestTemplate restTemplate;

    public CurrencyConversionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Acquires the exchange rates for the given base and target currencies from the RESTful API.
     * 
     * @param baseCurrency currency to exchange from
     * @param targetCurrency currency to exchange to
     * @return an ExchangeRatesResponse object with the corresponding exchange rates
     * @throws IllegalArgumentException if unable to fetch rates for given parameters
     */
    public ExchangeRatesResponse getExchangeRates(String baseCurrency, String targetCurrency) throws IllegalArgumentException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiBase)
            .queryParam("base", baseCurrency)
            .queryParam("symbols", targetCurrency)
            .queryParam("access_key", apiKey);
        String url = builder.toUriString();
        ExchangeRatesResponse response = restTemplate.getForObject(url, ExchangeRatesResponse.class);

        if (response != null && response.getRates() != null && (response.getRates().size() == 2)) {
            return response;
        } else {
            throw new IllegalArgumentException("Unable to fetch exchange rates for given parameters.\n");
        }
    }

    /**
     * Converts the given base currency to the given target currency for the given amount.
     * 
     * @param baseCurrency the currency type to convert from
     * @param targetCurrency the currency type to convert to
     * @param amount the amount of the base currency to convert
     * @return the converted currency
     */
    public double convertCurrency(String baseCurrency, String targetCurrency, double amount) throws IllegalArgumentException {
        ExchangeRatesResponse exchangeRates = getExchangeRates(baseCurrency, targetCurrency);
        Map<String, Double> rates = exchangeRates.getRates();
        double baseRate = rates.get(baseCurrency);
        double targetRate = rates.get(targetCurrency);
        return (amount * (1 / baseRate)) * targetRate;
    }
}
