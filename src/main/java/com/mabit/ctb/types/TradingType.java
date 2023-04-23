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
