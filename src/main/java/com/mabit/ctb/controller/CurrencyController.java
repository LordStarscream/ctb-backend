package com.mabit.ctb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.service.CurrencyService;

@RestController
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    @GetMapping("/currency")
    public Iterable<Currency> getAllCurrencys() {
        return currencyService.getAllCurrencys();
    }

    @PostMapping("/currency")
    public void addDeals(@RequestBody Currency currency) {
        currencyService.addCurrency(currency);
    }
}