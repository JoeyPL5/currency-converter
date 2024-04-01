package com;


import java.text.DecimalFormat;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Util.DateString;
import com.Util.StringUtil;

@RestController
public class CurrencyConversionController {

    private final CurrencyConversionService conversionService;

    public CurrencyConversionController(CurrencyConversionService currencyConversionService) {
        this.conversionService = currencyConversionService;
    }

    /**
     * Establishes the convert endpoint and utilizes the CurrencyConversionService to output the converted currency values 
     * for the given request parameters.
     * 
     * @param amount the value of the currency to convert
     * @param date the date to retrieve historical rates from (optional)
     * @param from the base currency to convert from   
     * @param to the target currency(s) to convert to
     * @return the result of the currency conversion(s)
     * @throws IllegalArgumentException if the conversion was unsuccessful for the given parameters
     */
    @GetMapping(value = "/convert", produces = MediaType.TEXT_PLAIN_VALUE)
    public String convertCurrency(@RequestParam double amount, @RequestParam(required = false) String date, @RequestParam String from, @RequestParam String... to) throws IllegalArgumentException {
        Map<String, Double> convertedAmounts;
        if (date != null) {
            convertedAmounts = conversionService.convertCurrency(amount, new DateString(date), from, to);
        } else {
            convertedAmounts = conversionService.convertCurrency(amount, from, to);
        }
        String output = amount + " " + from + " equals:\n";
        DecimalFormat df = new DecimalFormat("#.##");
        for (Map.Entry<String, Double> entry: convertedAmounts.entrySet()) {
            if (entry.getValue() != -1) {
                output += df.format(entry.getValue()) + " " + entry.getKey() + "\n";
            } else {
                output += "Unable to fetch results for " + entry.getKey() + "\n";
            }
        }
        return output;
    }

    /**
     * Establishes the endpoint for retrieving the available currencies from the API.
     * @return the available currencies
     */
    @GetMapping("/currencies")
    public String[] getCurrencies() {
        return conversionService.getAvailableExchangeRates();
    }

    /**
     * 
     * 
     * @param startDate
     * @param endDate
     * @param baseCurrency
     * @param targetCurrency
     * @return
     */
    @GetMapping("/historical-rates")
    public Map<String, Double> getHistoricalExchangeRates(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") DateString startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") DateString endDate,
            @RequestParam("baseCurrency") String baseCurrency,
            @RequestParam("targetCurrency") String targetCurrency) {
        Map<DateString, Double> historicalRates = conversionService.getHistoricalExchangeRates(startDate, endDate, baseCurrency, targetCurrency);
        return StringUtil.dateStringMapToString(historicalRates);
    }
}
