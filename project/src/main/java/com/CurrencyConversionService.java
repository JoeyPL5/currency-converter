package com;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
     * Acquires the exchange rates from the base currency to the target currencies from the RESTful API.
     * 
     * @param baseCurrency currency to exchange from
     * @param targetCurrencies currencies to exchange to
     * @return an ExchangeRatesResponse object with the corresponding exchange rates
     * @throws IllegalArgumentException if unable to fetch rates for given parameter
     */
    public ExchangeRatesResponse getExchangeRates(String baseCurrency, String... targetCurrencies) throws IllegalArgumentException {
        String url = buildQuery(baseCurrency, targetCurrencies);
        return sendExchangeRatesQuery(url);
    }

    /**
     * Acquires the historical rate from the base currency to the target currencies for the given date.
     * 
     * @param date the date to retrieve historical rates from
     * @param baseCurrency currency to exchange from
     * @param targetCurrencies currencies to exchange to
     * @return an ExchangeRatesResponse object with the corresponding exchange rates
     */
    public ExchangeRatesResponse getExchangeRates(DateString date, String baseCurrency, String... targetCurrencies) throws IllegalArgumentException {
        String url = buildQuery(date, baseCurrency, targetCurrencies);
        return sendExchangeRatesQuery(url);
    }

    /**
     * Builds a query to the API based on the given parameters.
     * 
     * @param date the date to retrieve historical rates from
     * @param baseCurrency currency to exchange from
     * @param targetCurrencies currencies to exchange to
     * @return a String containing a URL for querying the API
     */
    private String buildQuery(DateString date, String baseCurrency, String... targetCurrencies) {
        String targets = StringUtil.commaSeparatedString(targetCurrencies);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiBase)
            .queryParam("apikey", apiKey)
            .queryParam("base_currency", baseCurrency)
            .queryParam("currencies", targets)
            .queryParam("date", date.getDate());
        String url = builder.toUriString();
        return url;
    }

    /**
     * Builds a query to the API based on the given parameters.
     * 
     * @param baseCurrency currency to exchange from
     * @param targetCurrencies currencies to exchange to
     * @return a String containing a URL for querying the API
     */
    private String buildQuery(String baseCurrency, String... targetCurrencies) {
        String targets = StringUtil.commaSeparatedString(targetCurrencies);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiBase)
            .queryParam("apikey", apiKey)
            .queryParam("base_currency", baseCurrency)
            .queryParam("currencies", targets);
        String url = builder.toUriString();
        return url;
    }

    /**
     * Sends the given query to the exchange rates API and retrieves the response.
     * 
     * @param url the query to send
     * @return an ExchangeRatesResponse object
     */
    private ExchangeRatesResponse sendExchangeRatesQuery(String url) {
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
     * Converts the given base currency to the given target currency(s) for the given amount.
     * 
     * @param amount the amount of the base currency to convert
     * @param baseCurrency the currency type to convert from
     * @param targetCurrencies the currency type to convert to
     * @return a Map of the converted currency(s)
     * @throws IllegalArgumentException if unable to convert currency with given parameters
     */
    public Map<String, Double> convertCurrency(double amount, String baseCurrency, String... targetCurrencies) throws IllegalArgumentException {
        ExchangeRatesResponse response = getExchangeRates(baseCurrency, targetCurrencies);
        return convertEachRate(amount, response);
    }

    /**
     * Converts the base currency to the given target currency(s) for the given amount on the given date.
     * 
     * @param amount the amount of the base currency to convert
     * @param date the date of the historical exchange rate
     * @param baseCurrency the currency type to convert from
     * @param targetCurrencies the currency type to convert to
     * @return a Map of the converted currency(s)
     * @throws IllegalArgumentException if unable to convert currency with given parameters
     */
    public Map<String, Double> convertCurrency(double amount, DateString date, String baseCurrency, String... targetCurrencies) throws IllegalArgumentException {
        ExchangeRatesResponse response = getExchangeRates(date, baseCurrency, targetCurrencies);
        return convertEachRate(amount, response);
    }

    /**
     * Converts the amount based on each of the given exchange rates. 
     * 
     * @param amount the original amount to convert
     * @param exchangeRates the exchange rates to use for conversion
     * @return a Map of the conversion results
     */
    private Map<String, Double> convertEachRate(double amount, ExchangeRatesResponse exchangeRates) {
        Map<String, Double> rates = exchangeRates.getData();
        Set<String> targets = rates.keySet();
        HashMap<String, Double> convertedAmounts = new HashMap<String, Double>();
        for (String target : targets) {
            try {
                convertedAmounts.put(target, amount * rates.get(target));
            } catch (NullPointerException e) {
                convertedAmounts.put(target, -1.0);
            }
        }
        return convertedAmounts;
    }
}
