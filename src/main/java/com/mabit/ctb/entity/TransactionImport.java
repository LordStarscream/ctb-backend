/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mabit.ctb.entity;

import com.mabit.ctb.types.TradeDirection;
import com.mabit.ctb.types.TransactionType;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
@Entity
@Table(name = "TransactionImport")
public class TransactionImport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private String exchange;

    private Double inValue;

    private String inCurrency;

    private Double outValue;

    private String outCurrency;

    private Double fee;

    private String feeCurrency;

    @OneToOne(orphanRemoval = false)
    private FiatExchangeRate inFiatExchange;

    @OneToOne(orphanRemoval = false)
    private FiatExchangeRate outFiatExchange;

    private LocalDateTime dateTime;

    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public Double getInValue() {
        return inValue;
    }

    public void setInValue(Double inValue) {
        this.inValue = inValue;
    }

    public String getInCurrency() {
        return inCurrency;
    }

    public void setInCurrency(String inCurrency) {
        this.inCurrency = inCurrency;
    }

    public Double getOutValue() {
        return outValue;
    }

    public void setOutValue(Double outValue) {
        this.outValue = outValue;
    }

    public String getOutCurrency() {
        return outCurrency;
    }

    public void setOutCurrency(String outCurrency) {
        this.outCurrency = outCurrency;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getFeeCurrency() {
        return feeCurrency;
    }

    public void setFeeCurrency(String feeCurrency) {
        this.feeCurrency = feeCurrency;
    }

    public FiatExchangeRate getInFiatExchange() {
        return inFiatExchange;
    }

    public void setInFiatExchange(FiatExchangeRate inFiatExchange) {
        this.inFiatExchange = inFiatExchange;
    }

    public FiatExchangeRate getOutFiatExchange() {
        return outFiatExchange;
    }

    public void setOutFiatExchange(FiatExchangeRate outFiatExchange) {
        this.outFiatExchange = outFiatExchange;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public TransactionImport() {
    }

    public TransactionImport(TransactionType type, String exchange, Double inValue, String inCurrency, Double outValue, String outCurrency, Double fee, String feeCurrency, FiatExchangeRate inFiatExchange, FiatExchangeRate outFiatExchange, LocalDateTime dateTime, String comment) {
        this.type = type;
        this.exchange = exchange;
        this.inValue = inValue;
        this.inCurrency = inCurrency;
        this.outValue = outValue;
        this.outCurrency = outCurrency;
        this.fee = fee;
        this.feeCurrency = feeCurrency;
        this.inFiatExchange = inFiatExchange;
        this.outFiatExchange = outFiatExchange;
        this.dateTime = dateTime;
        this.comment = comment;
    }
}
