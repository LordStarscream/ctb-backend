package com.mabit.ctb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.repository.CurrencyRepository;

@Service
public class TradeSevice {

    @Autowired
    private  CurrencyRepository currencyRepository;


    public void AddCurrency(Currency currency){
        currencyRepository.save(currency);
    }

    public void AddCurrency(String ticker, String name){
        var currency = new Currency(ticker, name);
        AddCurrency(currency);
    }

    public Iterable<Currency> GetAllCurrency(){
        return currencyRepository.findAll();
    }

    public Currency GetCurrencyByTicker(String ticker){
        return currencyRepository.findByTicker(ticker);
    }


}
