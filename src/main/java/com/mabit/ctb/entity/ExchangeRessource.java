/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mabit.ctb.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */

@Entity
@Table(name="exchangeRessource")
public class ExchangeRessource implements Serializable{
    
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @OneToOne(orphanRemoval=false)
    private Location exchange;
    
    private String ressource;
    
    private LocalDateTime date;


    public ExchangeRessource() {
    }

    public ExchangeRessource(Location exchange, String ressource, LocalDateTime date) {
        this.exchange = exchange;
        this.ressource = ressource;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Location getExchange() {
        return exchange;
    }

    public void setExchange(Location exchange) {
        this.exchange = exchange;
    }

    public String getRessource() {
        return ressource;
    }

    public void setRessource(String ressource) {
        this.ressource = ressource;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
}
