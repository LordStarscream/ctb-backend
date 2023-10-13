package com.mabit.ctb.service;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabit.ctb.beans.TransactionInfo;
import com.mabit.ctb.consumer.AssetCoingekoConsumer;
import com.mabit.ctb.entity.FiatExchangeRate;
import com.mabit.ctb.repository.FiatExchangeRateRepository;

@Service
public class FiatExchangeRateService {

    @Autowired
    private AssetCoingekoConsumer dateExchange;

    @Autowired
    private FiatExchangeRateRepository fiatRepository;

    public FiatExchangeRate checkFiatRate(TransactionInfo info, FiatExchangeRate currentRate, Boolean autoSetRate) throws NotImplementedException{
        FiatExchangeRate exValue = currentRate;
        if (Boolean.TRUE.equals(autoSetRate)) {
            Double factor = dateExchange.getExchangeRateAtDate(info.getCurrency(), info.getDateTime());
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
            //exValue = importController.checkFiatRate(info, currentRate);
            throw new NotImplementedException("autoSetRate is mendetory curently");
        }
        return exValue;
    }

    public FiatExchangeRate checkFiatRate(TransactionInfo info, Boolean autoSetRate) throws NotImplementedException, NullPointerException{
        if (Boolean.TRUE.equals(autoSetRate)) {
            Double factor = dateExchange.getExchangeRateAtDate(info.getCurrency(), info.getDateTime());
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

}
