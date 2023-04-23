/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mabit.ctb.entity;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
 @Entity
 @Table(name="TradingImages")
 public class TradingImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String address;
    
    private Date imageTime;
    
    public TradingImage(){
        
    }
    
    public TradingImage(Long id, String name, String address, Date dateTime) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.imageTime = dateTime;
    }

    public TradingImage(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public TradingImage(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    @Override
    public String toString(){
        return this.name;
    }
    
    
}
