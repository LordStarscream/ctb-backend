package com.mabit.ctb.utils;

public class StringUtils {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
    public static String removeCurrencySymbol(String str, char symbol){
        return str.replace(symbol, ' ').trim();
    }
    public static String removeEuro(String str){
        return removeCurrencySymbol(str, '€');
    }
    public static String removeUtc(String str){
        return str.replace("UTC","").trim();
    }
    private StringUtils(){}
}
