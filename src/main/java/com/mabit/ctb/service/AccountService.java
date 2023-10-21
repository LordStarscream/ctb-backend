package com.mabit.ctb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.repository.CurrencyRepository;

@Service
public class AccountService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Value("${spring.application.config.currency}")
    private String fiatCurrencyTicker;

    private Currency fiatCurrency;

    public Currency getCurrency(String ticker){
        Currency currency = currencyRepository.findByTicker(ticker);
        if (currency == null) {
            //try to get the name from exchange api
            currency = currencyExchangeService.getCurrency(ticker);
        }
        return currency;
    }

    public Currency getBaseFiatCurrency() {
        if (fiatCurrency==null)
            fiatCurrency = getCurrency(this.fiatCurrencyTicker);
        return fiatCurrency;
    }

}
