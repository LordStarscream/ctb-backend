package com.mabit.ctb.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mabit.ctb.beans.TransactionImportInfo;
import com.mabit.ctb.dto.TransactionImportDto;
import com.mabit.ctb.entity.TransactionImport;
import com.mabit.ctb.entity.cfd.DealDto;
import com.mabit.ctb.service.TradeImportService;

@RestController
public class TradeImportController {

    @Autowired
    private TradeImportService tradeImportService;

    @GetMapping("/transactionImports")
    public Iterable<TransactionImportDto> getTransactionImports(){
        Iterable<TransactionImport> transactionImports = tradeImportService.getAllTransactionImports();
        List<TransactionImportDto> dto = StreamSupport.stream(transactionImports.spliterator(), false)
        .map(TransactionImportDto::new)
        .collect(Collectors.toList());
        return dto;
    }

    @GetMapping("/transactionImports/importAll")
    public Iterable<TransactionImportInfo> executeImportTransactions(){
        return tradeImportService.importTransactions(false);
   }
}