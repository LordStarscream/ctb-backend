package com.mabit.ctb.types;

public enum AccountType {
    crypto("crypto"),
    forex("forex");

    private String label;

    AccountType(String label){
        this.label = label;
    }

    @Override
    public String toString(){
        return label;
    }

}
