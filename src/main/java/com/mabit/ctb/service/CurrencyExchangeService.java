package com.mabit.ctb.service;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mabit.ctb.beans.TransactionInfo;
import com.mabit.ctb.consumer.AssetCoingekoConsumer;
import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.FiatExchangeRate;
import com.mabit.ctb.repository.CurrencyRepository;
import com.mabit.ctb.repository.FiatExchangeRateRepository;


@Service
public class CurrencyExchangeService {

    @Autowired
    private AssetCoingekoConsumer dataExchange;

    @Autowired
    private FiatExchangeRateRepository fiatRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    public Iterable<Currency> getAllCurrencys(){
        return currencyRepository.findAll();
    }

    @Transactional
    public FiatExchangeRate checkFiatRate(TransactionInfo info, FiatExchangeRate currentRate, Boolean autoSetRate) throws NotImplementedException{
        FiatExchangeRate exValue = currentRate;
        if (Boolean.TRUE.equals(autoSetRate)) {
            Double factor = dataExchange.getExchangeRateAtDate(info.getCurrency(), info.getDateTime());
            if (factor != null && factor != 0) {
                var fiatExchangeRate = new FiatExchangeRate(
                        info.getCurrency(),
                        info.getFiatCurrency(),
                        info.getLocation(),
                        factor,
                        info.getDateTime());
                exValue = fiatRepository.save(fiatExchangeRate);
            }
        } else {
            throw new NotImplementedException("autoSetRate is mendetory curently");
        }
        return exValue;
    }

    @Transactional
    public FiatExchangeRate checkFiatRate(TransactionInfo info, Boolean autoSetRate) throws NotImplementedException, NullPointerException{
        if (Boolean.TRUE.equals(autoSetRate)) {
            Double factor = dataExchange.getExchangeRateAtDate(info.getCurrency(), info.getDateTime());
            var fiatExchangeRate = new FiatExchangeRate(
                    info.getCurrency(),
                    info.getFiatCurrency(),
                    info.getLocation(),
                    factor,
                    info.getDateTime());
            return fiatRepository.save(fiatExchangeRate);
        } else
            throw new NotImplementedException("autoSetRate is mendetory curently");
    }

    public FiatExchangeRate save(FiatExchangeRate fiatExchangeRate){
        return fiatRepository.save(fiatExchangeRate);
    }

    @Transactional
    public Currency getCurrency(String ticker){
        String name = null;
        if (CurrencyService.baseCurrencies.containsKey(ticker))
            name = CurrencyService.baseCurrencies.get(ticker);
        else
            name = dataExchange.getCurrencyName(ticker);
        Currency currency = new Currency(ticker, name);
        return currencyRepository.save(currency);
    }

}
