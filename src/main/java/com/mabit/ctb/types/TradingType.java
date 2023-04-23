/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mabit.ctb.types;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
public enum TradingType {
    Investment("Investment"),
    Trade("Trade"),
    Holding("Holding"),
    Undefined("Undefined");

    private String label;

    TradingType(String label){
        this.label = label;
    }

    public String toString(){
        return label;
    }
}
