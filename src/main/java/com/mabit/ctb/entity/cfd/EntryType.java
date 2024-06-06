package com.mabit.ctb.entity.cfd;

public enum EntryType {
    In("in"),
    Out("out");

    private String label;

    EntryType(String label){
        this.label = label;
    }

    @Override
    public String toString(){
        return label;
    }
}
