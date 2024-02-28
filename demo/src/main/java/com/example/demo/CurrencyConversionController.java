package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConversionController {

    private final CurrencyConversionService conversionService;

    @Autowired
    public CurrencyConversionController(CurrencyConversionService currencyConversionService) {
        this.currencyConversionService = currencyConversionService;
    }

    @GetMapping("/convert")
    public String convertCurrency(@RequestParam String from, @RequestParam String to, double amount) {
        double convertedAmount = conversionService.convert(from, to, amount);
        return amount + " " + from + " equals " + convertedAmount + " " + to;
    }
}
