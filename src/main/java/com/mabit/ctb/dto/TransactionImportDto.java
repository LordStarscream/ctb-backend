package com.mabit.ctb.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.mabit.ctb.entity.TransactionImport;
import com.mabit.ctb.types.TransactionType;

import lombok.Data;

@Data
public class TransactionImportDto {

    private Long id;

    private TransactionType type;

    private String exchange;

    private Double inValue;

    private String inCurrency;

    private Double outValue;

    private String outCurrency;

    private Double fee;

    private String feeCurrency;

    private String dateTime;

    private String comment;

    public TransactionImportDto(TransactionImport transactionImport){
        this.id = transactionImport.getId();
        this.type = transactionImport.getType();
        this.exchange = transactionImport.getExchange();
        this.inValue = transactionImport.getInValue();
        this.inCurrency = transactionImport.getInCurrency();
        this.outValue = transactionImport.getOutValue();
        this.outCurrency = transactionImport.getOutCurrency();
        this.fee = transactionImport.getFee();
        this.feeCurrency = transactionImport.getFeeCurrency();
        this.dateTime = transactionImport.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
