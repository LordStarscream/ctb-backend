/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mabit.ctb.entity;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "FiatExchangeRate")
public class FiatExchangeRate implements Serializable{

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(orphanRemoval = false)
    private Currency cryptoCurrency;

    @OneToOne(orphanRemoval = false)
    private Currency fiatCurrency;
    
    @OneToOne(orphanRemoval = false)
    private Location exchange;    
    
    private Double factor;
    
    private LocalDateTime date;

    @OneToOne(orphanRemoval = false)
    @Nullable
    private ExchangeRessource ressource;

    public FiatExchangeRate() {
    }

    /** 
     * 
     * @param cryptoCurrency
     * @param fiatCurrency
     * @param exchange
     * @param factor
     * @param date 
     */
    public FiatExchangeRate(Currency cryptoCurrency, Currency fiatCurrency, Location exchange, Double factor, LocalDateTime date) {
        this.cryptoCurrency = cryptoCurrency;
        this.fiatCurrency = fiatCurrency;
        this.exchange = exchange;
        this.factor = factor;
        this.date = date;
    }

    public FiatExchangeRate(Currency cryptoCurrency, Currency fiatCurrency, Location exchange, LocalDateTime date) {
        this.cryptoCurrency = cryptoCurrency;
        this.fiatCurrency = fiatCurrency;
        this.exchange = exchange;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Currency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(Currency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    public Currency getFiatCurrency() {
        return fiatCurrency;
    }

    public void setFiatCurrency(Currency fiatCurrency) {
        this.fiatCurrency = fiatCurrency;
    }

    public ExchangeRessource getRessource() {
        return ressource;
    }

    public void setRessource(ExchangeRessource ressource) {
        this.ressource = ressource;
    }
    
    public Location getExchange() {
        return exchange;
    }

    public void setExchange(Location exchange) {
        this.exchange = exchange;
    }

    public Double getFactor() {
        return factor;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }        

}
