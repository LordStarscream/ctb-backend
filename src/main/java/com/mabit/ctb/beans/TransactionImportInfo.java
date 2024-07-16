package com.mabit.ctb.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.TransactionImport;
import com.mabit.ctb.entity.cfd.Deal;
import com.mabit.ctb.types.TransactionType;

import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionImportInfo {

    private TransactionImport transactionImport;
    private boolean importSuccess = false;
    private boolean fiatRateMissing = true;

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

    public TransactionImportInfo(TransactionImport transactionImport){
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

        this.importSuccess = true;
        this.fiatRateMissing = true;
    }
}
