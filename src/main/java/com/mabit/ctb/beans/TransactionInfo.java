package com.mabit.ctb.beans;

import java.time.LocalDateTime;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.FiatExchangeRate;
import com.mabit.ctb.entity.Location;
import com.mabit.ctb.types.TradeDirection;

/**
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
public class TransactionInfo {

    private TradeDirection direction;
    private Currency currency;
    private Double value;
    private Double fee;
    private LocalDateTime dateTime;
    private Currency fiatCurrency;
    private Location location;
    private FiatExchangeRate rate;

    public TransactionInfo(TradeDirection direction, Currency currency, Double value, Double fee, Location location, LocalDateTime dateTime) {
        this.direction = direction;
        this.currency = currency;
        this.value = value;
        this.fee = fee;
        this.location = location;
        this.dateTime = dateTime;
    }

    public TransactionInfo() {
    }

    public TradeDirection getDirection() {
        return direction;
    }

    public void setDirection(TradeDirection direction) {
        this.direction = direction;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getValue() {
        return value;
    }

    public void setPrice(Double price) {
        this.value = price;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public FiatExchangeRate getRate() {
        return rate;
    }

    public void setRate(FiatExchangeRate rate) {
        this.rate = rate;
    }

    public Currency getFiatCurrency() {
        return fiatCurrency;
    }

    public void setFiatCurrency(Currency fiatCurrency) {
        this.fiatCurrency = fiatCurrency;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
