package com.mabit.ctb.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mabit.ctb.dto.AccountDto;
import com.mabit.ctb.entity.Account;
import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.cfd.DealDto;
import com.mabit.ctb.service.AccountService;
import com.mabit.ctb.service.CurrencyExchangeService;
import com.mabit.ctb.service.cfd.TransactionService;
import com.mabit.ctb.service.cfd.TransactionServiceException;
import com.mabit.ctb.entity.AccountType;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    CurrencyExchangeService currencyService;

    @GetMapping("/accounts")
    public Iterable<AccountDto> getAllAccounts() {
        var accounts = accountService.getAllAccounts();
        return StreamSupport.stream(accounts.spliterator(), false)
                        .map(this::toDto)
                        .collect(Collectors.toList());
    }

    @PostMapping("/account")
    public void addDeals(@RequestBody AccountDto accountDto) {
        Account account = new Account();
        account.setName(accountDto.getName());
        account.setInformation(accountDto.getInformation());
        Currency currency = currencyService.getCurrency(accountDto.getReferenceCurrency());
        account.setReferenceCurrency(currency);
        account.setType(accountService.getAccountType(accountDto.getType()));
        accountService.addAccount(account);
    }

    @GetMapping("/accountTypes")
    public List<String> getAccountTypes() {
        return  StreamSupport.stream(accountService.getAccountTypes().spliterator(), false)
        .map(AccountType::getName).collect(Collectors.toList());
    }

    private AccountDto toDto(Account account){
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setName(account.getName());
        accountDto.setInformation(account.getInformation());
        accountDto.setReferenceCurrency(account.getReferenceCurrency().getTicker());
        accountDto.setType(account.getType().getName());
        return accountDto;
    }
}
