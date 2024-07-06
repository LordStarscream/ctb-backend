package com.mabit.ctb.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.repository.CurrencyRepository;


@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public static final Map<String, String> baseCurrencies = new HashMap<>();
    static {
        baseCurrencies.put("EUR", "Euro");
        baseCurrencies.put("USD", "United State dollar");
        baseCurrencies.put("GBP", "Britisches Pfund");
    }

    public Iterable<Currency> getAllCurrencys(){
        return currencyRepository.findAll();
    }

    public void addCurrency(Currency currency){
        var existing = currencyRepository.findByTicker(currency.getTicker());
        if (existing != null){
            existing.setName(currency.getName());
            currencyRepository.save(existing);
        }else{
            currencyRepository.save(currency);
        }
    }

}