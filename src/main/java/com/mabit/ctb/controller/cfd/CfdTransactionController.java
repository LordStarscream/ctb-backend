package com.mabit.ctb.controller.cfd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mabit.ctb.entity.cfd.Deal;
import com.mabit.ctb.service.cfd.TransactionService;
import com.mabit.ctb.service.cfd.TransactionServiceException;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class CfdTransactionController{

    @Autowired
    TransactionService transactionService;

    @GetMapping("/cfd/transactions")
    public Iterable<Deal> getAllTransactions() {
        return transactionService.getDealsOfAccount();
    }

    @GetMapping("/cfg/transactions/{account}")
    public Iterable<Deal> incomeReport(@PathVariable Long account) throws TransactionServiceException{
            return transactionService.getDealsOfAccount(account);
    }

    @PostMapping("/cfd/transactions")
    public void addDeals(@RequestBody List<Deal> deals) {
        transactionService.addDeals(deals);
    }

    @PostMapping("/cfd/transaction")
    public void addDeal(@RequestBody Deal deal) {
        transactionService.addDeal(deal);
    }

}
