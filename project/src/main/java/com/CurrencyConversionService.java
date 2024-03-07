package com;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.Util.StringUtil;

import com.google.gson.Gson;

@Service
public class CurrencyConversionService {
    private final RestTemplate restTemplate;
    
    private String apiKey;

    private String apiBase;

    public CurrencyConversionService(RestTemplate restTemplate, 
                                    @Value("${exchange-api.key}") String key, 
                                    @Value("${exchange-api.base}") String base) {
        this.restTemplate = restTemplate;
        this.apiKey = key;
        this.apiBase = base;
    }

    /**
     * Acquires the exchange rate from the base currency to the target currency(s) from the RESTful API.
     * 
     * @param baseCurrency currency to exchange from
     * @param targetCurrency currency(s) to exchange to
     * @return an ExchangeRatesResponse object with the corresponding exchange rate(s)
     * @throws IllegalArgumentException if unable to fetch rates for given parameter
     */
    public ExchangeRatesResponse getExchangeRates(String baseCurrency, String targetCurrency) throws IllegalArgumentException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiBase)
            .queryParam("base_currency", baseCurrency)
            .queryParam("currencies", targetCurrency)
            .queryParam("apikey", apiKey);
        String url = builder.toUriString();
    
        ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        String body = entity.getBody();
        Gson gson = new Gson();;
        ExchangeRatesResponse response = gson.fromJson(body, ExchangeRatesResponse.class);

        if (response != null && response.getData() != null) {
            return response;
        } else {
            throw new IllegalArgumentException("Unable to fetch exchange rates for given parameters.\n");
        }
    }

    /**
     * Acquires the exchange rates from the base currency to the target currencies from the RESTful API.
     * 
     * @param baseCurrency currency to exchange from
     * @param targetCurrencies currencies to exchange to
     * @return an ExchangeRatesResponse object with the corresponding exchange rates
     * @throws IllegalArgumentException if unable to fetch rates for given parameter
     */
    public ExchangeRatesResponse getExchangeRates(String baseCurrency, String... targetCurrencies) throws IllegalArgumentException {
        String targets = StringUtil.commaSeparatedString(targetCurrencies);
        return getExchangeRates(baseCurrency, targets);
    }

    /**
     * Converts the given base currency to the given target currency(s) for the given amount.
     * 
     * @param amount the amount of the base currency to convert
     * @param baseCurrency the currency type to convert from
     * @param targetCurrencies the currency type to convert to
     * @return a Map of the converted currency(s)
     */
    public Map<String, Double> convertCurrency(double amount, String baseCurrency, String... targetCurrencies) {
        int targetsLen = targetCurrencies.length;
        ExchangeRatesResponse exchangeRates = getExchangeRates(baseCurrency, targetCurrencies);
        Map<String, Double> rates = exchangeRates.getData();
        HashMap<String, Double> convertedAmounts = new HashMap<String, Double>();
        for (int i = 0; i < targetsLen; i++) {
            try {
                convertedAmounts.put(targetCurrencies[i], amount * rates.get(targetCurrencies[i]));
            } catch (NullPointerException e) {
                convertedAmounts.put(targetCurrencies[i], -1.0);
            }
        }
        return convertedAmounts;
    }
}
