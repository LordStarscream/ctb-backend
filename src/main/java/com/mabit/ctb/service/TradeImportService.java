package com.mabit.ctb.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.FiatExchangeRate;
import com.mabit.ctb.entity.Location;
import com.mabit.ctb.entity.TransactionImport;
import com.mabit.ctb.entity.Transaction;
import com.mabit.ctb.repository.CurrencyRepository;
import com.mabit.ctb.repository.LocationRepository;
import com.mabit.ctb.repository.TransactionImportRepository;
import com.mabit.ctb.repository.TransactionRepository;
import com.mabit.ctb.types.TradeDirection;
import com.mabit.ctb.types.TransactionType;

import lombok.extern.slf4j.Slf4j;

import com.mabit.ctb.beans.TransactionImportInfo;
import com.mabit.ctb.beans.TransactionInfo;

@Slf4j
@Service
public class TradeImportService {

    @Autowired
    private TransactionImportRepository transactionImportRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private FiatExchangeRateService fiatExchangeRateService;

    // Set current base Fiat Currency out of config
    @Value("${spring.application.config.currency}")
    private String fiatCurrencyTicker;

    private Currency fiatCurrency;

    public Iterable<TransactionImport> getAllTransactionImports(){
        return transactionImportRepository.findAll();
    }

    public List<TransactionImportInfo> importTransactions(Boolean autoFiatRate){
        List<TransactionImportInfo> resultList = new ArrayList<>();
        var imports = getAllTransactionImports();
        for (TransactionImport transactionImport : imports) {
            resultList.add(checkImport(transactionImport, autoFiatRate));
        }
        return resultList;
    }

    private TransactionImportInfo checkImport(TransactionImport transactionImport, Boolean autoFiat) {
        TransactionImportInfo transactionInfo = new TransactionImportInfo();
        transactionInfo.setImportSuccess(true);
        Transaction transaction = new Transaction();
        /*Deposit and Withdraw need no fiat calculation*/
        List<TransactionType> inOnly = Arrays.asList(TransactionType.Income, TransactionType.Deposit, TransactionType.Gift);
        List<TransactionType> outOnly = Arrays.asList(TransactionType.Donation, TransactionType.Lost, TransactionType.Withdraw);
        List<TransactionType> needFiat = Arrays.asList(TransactionType.Trade, TransactionType.Income, TransactionType.Gift,TransactionType.Donation,TransactionType.Lost);
        try {

            /*General all Types*/
            if (transactionImport.getType() != null) {
                transaction.setType(transactionImport.getType());
            } else {
               transactionInfo.setImportSuccess(false);
            }

            if (transactionImport.getDateTime() != null) {
                transaction.setDateTime(transactionImport.getDateTime());
            } else {
                transactionInfo.setImportSuccess(false);
            }

            if (!transactionImport.getExchange().isEmpty()) {
                Location location = locationRepository.findByName(transactionImport.getExchange());
                if (location == null) {
                    location = locationRepository.save(new Location(transactionImport.getExchange(), true));
                }
                transaction.setExchange(location);
            }

            //Fee is not set and is Null .. why by spot import phemex ?
            if (!transactionImport.getFeeCurrency().isEmpty() && transactionImport.getFee() != null) {
                Currency feeCurrency = currencyRepository.findByTicker(transactionImport.getFeeCurrency());
                if (feeCurrency == null) {
                    transactionInfo.setImportSuccess(false);
                    transactionInfo.getCurrency().add(feeCurrency);
                }else{
                    transaction.setFeeCurrency(feeCurrency);
                    transaction.setFee(transactionImport.getFee());
                }
            }

            /* all Types that not having only out values, so all in and also transactions with both*/
            if (inOnly.contains(transactionImport.getType())) {
                if (!transactionImport.getInCurrency().isEmpty()) {
                    Currency inCurrency = currencyRepository.findByTicker(transactionImport.getInCurrency());
                    transaction.setInCurrency(inCurrency);
                    transaction.setInValue(transactionImport.getInValue());
                }
                if (needFiat.contains(transactionImport.getType()) &&
                        (!transaction.getInCurrency().equals(getFiatCurrency()))) { //auslagern in methode, nur für welche die fiat benötigen und bei transaction bei einer mit fiat die rate direkt nehmen
                    TransactionInfo sellInfo
                            = new TransactionInfo(TradeDirection.Sell, transaction.getInCurrency(), transaction.getInValue(), transaction.getFee(), transaction.getExchange(), transaction.getDateTime());
                    FiatExchangeRate sellExchangeRate = fiatExchangeRateService.checkFiatRate(sellInfo, autoFiat);
                    transaction.setInFiatExchange(sellExchangeRate);
                }
            }
            /* all Types that not having only in values, so all out and also transactions with both*/
            if (outOnly.contains(transactionImport.getType())) {
                if (!transactionImport.getOutCurrency().isEmpty()) {
                    Currency outCurrency = currencyRepository.findByTicker(transactionImport.getOutCurrency());
                    transaction.setOutCurrency(outCurrency);
                    transaction.setOutValue(transactionImport.getOutValue());
                }
                if (needFiat.contains(transactionImport.getType()) &&
                        (!transaction.getOutCurrency().equals(getFiatCurrency()))) {
                    TransactionInfo buyInfo
                            = new TransactionInfo(TradeDirection.Buy, transaction.getOutCurrency(), transaction.getOutValue(), transaction.getFee(), transaction.getExchange(), transaction.getDateTime());
                    FiatExchangeRate buyExchangeRate = fiatExchangeRateService.checkFiatRate(buyInfo, autoFiat);
                    transaction.setOutFiatExchange(buyExchangeRate);
                }
            }
            /* Transaction now in own part .. TODO should completley be overthought */
            if (transactionImport.getType().equals(TransactionType.Trade)){
                if (!transactionImport.getInCurrency().isEmpty()) { //should always be set in a trade
                    Currency inCurrency = currencyRepository.findByTicker(transactionImport.getInCurrency());
                    transaction.setInCurrency(inCurrency);
                    transaction.setInValue(transactionImport.getInValue());
                }
                if (!transactionImport.getOutCurrency().isEmpty()) { //should always be set in a trade
                    Currency outCurrency = currencyRepository.findByTicker(transactionImport.getOutCurrency());
                    transaction.setOutCurrency(outCurrency);
                    transaction.setOutValue(transactionImport.getOutValue());
                }
                //always needs fiat
                if (transaction.getOutCurrency().equals(getFiatCurrency())){
                    var factor = transaction.getOutValue() / transaction.getInValue();
                    var rate = new FiatExchangeRate(
                            transaction.getInCurrency(),
                            transaction.getOutCurrency(), //fiatCurrency
                            transaction.getExchange(),
                            factor,
                            transaction.getDateTime());
                    rate = fiatExchangeRateService.save(rate);
                    transaction.setInFiatExchange(rate);
                }else{
                    if (transaction.getInCurrency().equals(getFiatCurrency())) {
                        var factor = transaction.getInValue() / transaction.getOutValue();
                        var rate = new FiatExchangeRate(
                                transaction.getInCurrency(),
                                transaction.getOutCurrency(), //fiatCurrency
                                transaction.getExchange(),
                                factor,
                                transaction.getDateTime());
                        rate = fiatExchangeRateService.save(rate);
                        transaction.setOutFiatExchange(rate);
                    }else{
                        TransactionInfo buyInfo
                                = new TransactionInfo(TradeDirection.Buy, transaction.getOutCurrency(), transaction.getOutValue(), transaction.getFee(), transaction.getExchange(), transaction.getDateTime());
                        FiatExchangeRate buyExchangeRate = fiatExchangeRateService.checkFiatRate(buyInfo, autoFiat);
                        transaction.setOutFiatExchange(buyExchangeRate);

                        TransactionInfo sellInfo
                                = new TransactionInfo(TradeDirection.Sell, transaction.getInCurrency(), transaction.getInValue(), transaction.getFee(), transaction.getExchange(), transaction.getDateTime());
                        FiatExchangeRate sellExchangeRate = fiatExchangeRateService.checkFiatRate(sellInfo, autoFiat);                        
                        transaction.setInFiatExchange(sellExchangeRate);
                    }
                }
            }
        } catch (Exception e) {
            log.error("checkImport",e);
            transactionInfo.setImportSuccess(false);
        }

        if (transactionInfo.isImportSuccess()) {
            transactionRepository.save(transaction);
            transactionImportRepository.delete(transactionImport);
        }

        return transactionInfo;
    }

    private Currency checkCurrency(String ticker) {
        Currency currency = currencyRepository.findByTicker(ticker);
        return currency;
    }

    private Currency getFiatCurrency() {
        if (fiatCurrency==null)
            fiatCurrency = checkCurrency(this.fiatCurrencyTicker);
        return fiatCurrency;
    }
}
