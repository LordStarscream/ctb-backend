package com.mabit.ctb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mabit.ctb.entity.Account;
import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.cfd.Deal;
import com.mabit.ctb.entity.cfd.DealDto;
import com.mabit.ctb.entity.cfd.EntryType;
import com.mabit.ctb.entity.cfd.TradeType;
import com.mabit.ctb.repository.AccountRepository;
import com.mabit.ctb.repository.CurrencyRepository;
import com.mabit.ctb.types.AccountType;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

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

    public Account getAccount(){
        var result = accountRepository.findAll();
        if(result.iterator().hasNext() == true){
            var cryptoAccount = StreamSupport.stream(result.spliterator(), false)
                                    .filter(a -> a.getType().equals(AccountType.crypto)).findFirst();
            if(cryptoAccount.isPresent())
                return cryptoAccount.get();
        }
        getBaseFiatCurrency();
        Account account = new Account(fiatCurrency);
        return accountRepository.save(account);
    }

    public Iterable<Account> getAllAccounts(){
        var accounts = accountRepository.findAll();
        return accounts;
    }

    public void addAccount(Account account){
        var dbAccount = accountRepository.findById(account.getId());
        if (dbAccount.isPresent()){
            var a = dbAccount.get();
            a.setInformation(account.getInformation());
            a.setReferenceCurrency(account.getReferenceCurrency());
            accountRepository.save(a);
        }else{
            accountRepository.save(account);
        }
    }

    public Iterable<AccountType> getAccountTypes(){
        return List.of(AccountType.values());
    }

}
