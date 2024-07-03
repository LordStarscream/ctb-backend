package com.mabit.ctb.controller.cfd;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mabit.ctb.entity.cfd.Deal;
import com.mabit.ctb.entity.cfd.DealDto;
import com.mabit.ctb.service.cfd.TransactionService;
import com.mabit.ctb.service.cfd.TransactionServiceException;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class CfdTransactionController{

    @Autowired
    TransactionService transactionService;

    @GetMapping("/cfd/transactions")
    public Iterable<DealDto> getAllTransactions() {
        var deals = transactionService.getDealsOfAccount();
        List<DealDto> dealsDto = StreamSupport.stream(deals.spliterator(), false)
        .map(DealDto::new)
        .collect(Collectors.toList());
        return dealsDto;
    }

    @GetMapping("/cfg/transactions/{account}")
    public Iterable<DealDto> incomeReport(@PathVariable Long account) throws TransactionServiceException{
        var deals = transactionService.getDealsOfAccount(account);
        List<DealDto> dealsDto = StreamSupport.stream(deals.spliterator(), false)
        .map(DealDto::new)
        .collect(Collectors.toList());
        return dealsDto;
    }

    @PostMapping("/cfd/transactions")
    public void addDeals(@RequestBody List<DealDto> deals) {
        transactionService.updateDeals(deals);
    }

    @PostMapping("/cfd/transaction")
    public void addDeal(@RequestBody DealDto deal) {
        transactionService.addDeal(deal);
    }

}
