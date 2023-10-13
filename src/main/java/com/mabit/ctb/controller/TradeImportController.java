package com.mabit.ctb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mabit.ctb.entity.TransactionImport;
import com.mabit.ctb.service.TradeImportService;

@RestController
public class TradeImportController {

    @Autowired
    private TradeImportService tradeImportService;

    @GetMapping("/transactionImports")
    public Iterable<TransactionImport> getTransactionImports(){
        return tradeImportService.getAllTransactionImports();
    }
}
