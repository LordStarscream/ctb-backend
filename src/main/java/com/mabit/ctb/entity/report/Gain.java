/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mabit.ctb.entity.report;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.Location;
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
@Table(name = "Gain")
public class Gain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double ammount;
    
    @OneToOne(orphanRemoval=false)
    private Currency currency;
    
    private LocalDateTime inDateTime;
    
    private LocalDateTime outDateTime;
    
    private String shortLong;
    
    @OneToOne(orphanRemoval=false)
    private Location buyAt;
    
    @OneToOne(orphanRemoval=false)
    private Location sellAt;

    // Erlös
    private Double proceeds;
    
    private Double costbasis;
    
    private Double profit;

    public Gain() {
    }

    public Gain(Double ammount, Currency currency, LocalDateTime inDateTime, LocalDateTime outDateTime, String shortLong, Location buyAt, Location sellAt, Double proceeds, Double costbasis, Double profit) {
        this.ammount = ammount;
        this.currency = currency;
        this.inDateTime = inDateTime;
        this.outDateTime = outDateTime;
        this.shortLong = shortLong;
        this.buyAt = buyAt;
        this.sellAt = sellAt;
        this.proceeds = proceeds;
        this.costbasis = costbasis;
        this.profit = profit;
    }

    public Long getId() {
        return id;
    }

    public Double getAmmount() {
        return ammount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public LocalDateTime getInDateTime() {
        return inDateTime;
    }

    public LocalDateTime getOutDateTime() {
        return outDateTime;
    }

    public String getShortLong() {
        return shortLong;
    }

    public Location getBuyAt() {
        return buyAt;
    }

    public Location getSellAt() {
        return sellAt;
    }

    public Double getProceeds() {
        return proceeds;
    }

    public Double getCostbasis() {
        return costbasis;
    }

    public Double getProfit() {
        return profit;
    }

    

}
