/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mabit.ctb.entity;

import java.util.Locale;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author Mario Bittner
 */
@Entity
@Table(name="account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Double value;
    @OneToOne(orphanRemoval=false)
    private Currency referenceCurrency;   
    private Locale location;

    public Account(){}
    public Account(Double value, Currency referenceCurrency, Locale location) {
        this.value = value;
        this.referenceCurrency = referenceCurrency;
        this.location = location;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Currency getReferenceCurrency() {
        return referenceCurrency;
    }

    public void setReferenceCurrency(Currency referenceCurrency) {
        this.referenceCurrency = referenceCurrency;
    }

    public Locale getLocation() {
        return location;
    }

    public void setLocation(Locale location) {
        this.location = location;
    }
    
    
}
