package com.mabit.ctb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.service.TradeSevice;

@RestController
public class TransactionController {

    @Autowired
    TradeSevice tradeService;

    @GetMapping("/transaction/currencys")
    public Iterable<Currency> getAllCurrencies() {
        return tradeService.getAllCurrency();
    }

    @PostMapping("/transaction/currency")
    public void createCurrency(String name, String ticker) {
        tradeService.addCurrency(ticker, name);
   }

}
