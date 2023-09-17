package com.mabit.ctb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.repository.CurrencyRepository;

@Service
public class TradeSevice {

    @Autowired
    private  CurrencyRepository currencyRepository;

    public void addCurrency(Currency currency){
        currencyRepository.save(currency);
    }

    public void addCurrency(String ticker, String name){
        var currency = new Currency(ticker, name);
        addCurrency(currency);
    }

    public Iterable<Currency> getAllCurrency(){
        return currencyRepository.findAll();
    }

    public Currency getCurrencyByTicker(String ticker){
        return currencyRepository.findByTicker(ticker);
    }
}
