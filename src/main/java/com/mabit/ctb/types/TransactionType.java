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
public enum TransactionType {
    Trade("Trade"), // Trade
    Deposit("Deposit"), // Einzahlung
    Withdraw("Withdraw"), // Auszahlung
    Gift("Gift"), // Geschenk bekommen
    Donation("Donation"), // Verschenkung bezahlung
    Income("Income"), // Verdienst (GAS)
    Lost("Lost"); // Verlust


    private String label;

    TransactionType(String label){
        this.label = label;
    }

    @Override
    public String toString(){
        return label;
    }
}
