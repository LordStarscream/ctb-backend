package com.mabit.ctb.entity.cfd;

public enum TradeType {
    Buy("buy"),
    Sell("sell");

    private String label;

    TradeType(String label){
        this.label = label;
    }

    @Override
    public String toString(){
        return label;
    }
}
