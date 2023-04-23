/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mabit.ctb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
 @Entity
 @Table(name="location")
 public class Location {
    
    @Id
    @NotNull
    private String name;
    
    private Boolean isExchange;
    
    private String information;   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isExchange() {
        return isExchange;
    }

    public void setExchange(Boolean exchange) {
        this.isExchange = exchange;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
    
    public Location(){
        
    }

    public Location(String name, Boolean isExchange, String information) {
        this.name = name;
        this.isExchange = isExchange;
        this.information = information;
    }
    
    public Location(String name, Boolean isExchange) {
        this.name = name;
        this.isExchange = isExchange;
    }

    
    @Override
    public String toString(){
        return this.name;
    }
    
    
}
