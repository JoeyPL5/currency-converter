package com;


import java.text.DecimalFormat;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * @param from the base currency to convert from   
     * @param to the target currency(s) to convert to
     * @return the result of the currency conversion(s)
     */
    @GetMapping(value = "/convert", produces = MediaType.TEXT_PLAIN_VALUE)
    public String convertCurrency(@RequestParam double amount, @RequestParam String from, @RequestParam String... to) {
        Map<String, Double> convertedAmounts = conversionService.convertCurrency(amount, from, to);
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
}
