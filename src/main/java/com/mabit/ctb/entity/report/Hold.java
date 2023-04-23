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
@Table(name = "Hold")
public class Hold implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double ammount;
    
    @OneToOne(orphanRemoval=false)
    private Currency inCurrency;
    
    private LocalDateTime dateTime;
    
    @OneToOne(orphanRemoval=false)
    private Location location;
    
    private Double factor;

    public Hold() {
    }

    public Hold(Double ammount, Currency inCurrency, LocalDateTime dateTime, Location location, Double factor) {
        this.ammount = ammount;
        this.inCurrency = inCurrency;
        this.dateTime = dateTime;
        this.location = location;
        this.factor = factor;
    }
    public void setAmmount(Double ammount){
        this.ammount = ammount;
    }

    public Double getAmmount() {
        return ammount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Location getLocation() {
        return location;
    }

    public Double getFactor() {
        return factor;
    }
}
