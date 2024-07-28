package com.mabit.ctb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mabit.ctb.entity.Account;
import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.repository.AccountRepository;
import com.mabit.ctb.repository.AccountTypeRepository;
import com.mabit.ctb.repository.CurrencyRepository;
import com.mabit.ctb.entity.AccountType;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

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

    public Account getCryptoAccount(){
        var result = accountRepository.findByIsCryptoAccount(true);
        if(result.iterator().hasNext()){
            var cryptoAccount = result.iterator().next();
            return cryptoAccount;
        }
        getBaseFiatCurrency();
        var type = accountTypeRepository.findByName("Crypto");
        if (type.isPresent()){
            Account account = new Account(fiatCurrency, type.get());
            return accountRepository.save(account);
        }
        var newType = accountTypeRepository.save(new AccountType("Crypto"));

        Account account = new Account(fiatCurrency, newType);
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
/*
    public Iterable<AccountType> getAccountTypes(){
        return List.of(AccountType.values());
    }
*/
    public Iterable<AccountType> getAccountTypes(){
        return accountTypeRepository.findAll();
    }

    public AccountType getAccountType(String name){
        return accountTypeRepository.findByName(name).get();
    }

}
