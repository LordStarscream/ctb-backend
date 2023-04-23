/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mabit.ctb.entity.report;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.Location;
import com.mabit.ctb.types.TransactionType;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
@Entity
@Table(name = "Income")
public class Income implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double ammount;
    
    @OneToOne(orphanRemoval=false)
    private Currency currency;
    
    private LocalDateTime inDateTime;
    
    
    @OneToOne(orphanRemoval=false)
    private Location inAt;
    
    private TransactionType type;

    private String info;
    
    // Wert bei eingang in EUR
    private Double worthAtIncome;
    
    

    public Income() {
    }

    public Income(Double ammount, Currency currency, LocalDateTime inDateTime, Location inAt, TransactionType type, String info, Double worthAtIncome) {
        this.ammount = ammount;
        this.currency = currency;
        this.inDateTime = inDateTime;
        this.inAt = inAt;
        this.type = type;
        this.info = info;
        this.worthAtIncome = worthAtIncome;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }    

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    

    public Double getAmmount() {
        return ammount;
    }

    public void setAmmount(Double ammount) {
        this.ammount = ammount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDateTime getInDateTime() {
        return inDateTime;
    }

    public void setInDateTime(LocalDateTime inDateTime) {
        this.inDateTime = inDateTime;
    }

    public Location getInAt() {
        return inAt;
    }

    public void setInAt(Location inAt) {
        this.inAt = inAt;
    }

    public Double getWorthAtIncome() {
        return worthAtIncome;
    }

    public void setWorthAtIncome(Double worthAtIncome) {
        this.worthAtIncome = worthAtIncome;
    }
    

   

}
