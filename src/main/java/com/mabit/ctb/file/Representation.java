/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mabit.ctb.file;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
public class Representation {

    @Value("${spring.application.seperator}")
    static char decimalSeparator;

    static NumberFormat formatterPrice = new DecimalFormat("#,##0.00;-#",(new DecimalFormatSymbols(Locale.GERMANY)));
    //static NumberFormat formatterPricePos = new DecimalFormat("+#,##0.00;-#");
    static NumberFormat formatterPercent = new DecimalFormat("+##0.00;-#");

    static NumberFormat formatterCrypto = new DecimalFormat("#,##0.000000;-#",(new DecimalFormatSymbols(Locale.GERMANY)));

    private static double NullToZero(Double value){
        return (value != null)?value:0;
    }

    public static String getPriceFormat(Double price){
        return (price != null)?formatterPrice.format(price):null;
    }

    public static Double getValueFromPriceFormat(String price) throws ParseException{
        return (!price.isEmpty())?(formatterPrice.parse(price).doubleValue()):null;
    }

    public static String getPercentFormat(Double percent){
        return (percent != null)?formatterPercent.format(percent):null;
    }

    public static String getCryptoFormat(Double price){
        return  (price != null)?formatterCrypto.format(price):null;
    }

    public static String getProfit(Double buyPrice, Double sellPrice, Double buyFee, Double sellFee, boolean cleaned){
        return getPriceFormat(calculateProfit(buyPrice,sellPrice,buyFee,sellFee,cleaned));
    }

    public static Double calculateProfit(Double buyPrice, Double sellPrice, Double buyFee, Double sellFee, boolean cleaned){
        buyFee = NullToZero(buyFee);
        sellFee = NullToZero(sellFee);
        Double profit = sellPrice - buyPrice;
        if (cleaned){
            profit-= buyFee;
            profit-= sellFee;
        }
        return profit;
    }

    public static String getProfitPercentage(Double buyPrice, Double sellPrice, Double buyFee, Double sellFee, boolean cleaned){
        return getPercentFormat(calculateProfitPercentage(buyPrice,sellPrice,buyFee,sellFee,cleaned));
    }

    public static Double calculateProfitPercentage(Double buyPrice, Double sellPrice, Double buyFee, Double sellFee, boolean cleaned){
        buyFee = NullToZero(buyFee);
        sellFee = NullToZero(sellFee);
        Double percentage = ((100 / buyPrice) * sellPrice);
        if (cleaned)
            return (percentage-buyFee-sellFee)-100;

        return percentage-100;
    }

    public static NumberFormat PriceNumberFormat(){
        return formatterPrice;
    }

    public static NumberFormat PercentNumberFormat(){
        return formatterPercent;
    }
}
