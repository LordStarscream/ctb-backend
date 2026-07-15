package com.mabit.ctb.file;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mabit.ctb.entity.TransactionImport;
import com.mabit.ctb.types.TransactionType;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
@Service
@Qualifier("Binance")
public class BinanceImport extends FileImport{


    //private Dictionary<String, Currency> currencyMapping;
    private Dictionary<String, TransactionType> typeMapping;
    //private Dictionary<String, Location> locationMapping;

    public BinanceImport() {
        initDictionaries();
    }

    private void initDictionaries() {
        typeMapping = new Hashtable<String, TransactionType>();
        typeMapping.put("Deposit", TransactionType.Deposit);
        typeMapping.put("Withdraw", TransactionType.Withdraw);
        //typeMapping.put("Binance Convert", TransactionType.Trade);
        //typeMapping.put("Small Assets Exchange BNB", TransactionType.Trade);
        typeMapping.put("Distribution", TransactionType.Income);
        typeMapping.put("Staking Rewards", TransactionType.Income);

        typeMapping.put("Binance Convert", TransactionType.Decission);
        typeMapping.put("Transaction Revenue", TransactionType.Deposit);
        typeMapping.put("Transaction Fee", TransactionType.Fee);
        typeMapping.put("Transaction Sold", TransactionType.Withdraw);
    }

    @Override
    protected TransactionImport entryToEntity(String[] entry) {
        TransactionImport transaction = new TransactionImport();
        var type = typeMapping.get(entry[3]);

        if (type == TransactionType.Decission)
        {
            var value = Parse.stringToDouble(entry[5]);
            if (value > 0) {
                type = TransactionType.Deposit;
            }
            else {
                type = TransactionType.Withdraw;
            } 
        }

        if( type == TransactionType.Fee)
          type = TransactionType.Withdraw;
/*
        if (type == TransactionType.Income)
        {
            transaction.setType(type);
            transaction.setInValue(Parse.stringToDoublePositive(entry[5]));
            transaction.setInCurrency(entry[4]);
            //Transfer
            //Die Gebüren sollten nur beim senden anfallen, wenn bei Deposit würden sie wohl doppelt berechnet werden
            transaction.setFee(Parse.stringToDoublePositive(entry[8]));
            transaction.setFeeCurrency(entry[9]);
            transaction.setExchange(getName());
            if(entry.length >= 7)
                transaction.setComment(entry[6]);

            DateTimeFormatter formatter = null;
            LocalDateTime dateTime = null;

            try {
                formatter = DateTimeFormatter.ISO_DATE_TIME;
                dateTime = LocalDateTime.parse(entry[1], formatter);
            } catch (Exception ex) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                dateTime = LocalDateTime.parse(entry[1], formatter);
            }

            transaction.setDateTime(dateTime);
        }
*/
        if (type == TransactionType.Withdraw)
        {
            transaction.setType(type);
            transaction.setOutValue(Parse.stringToDoublePositive(entry[5]));
            transaction.setOutCurrency(entry[4]);
            //Transfer
            //Die Gebüren sollten nur beim senden anfallen, wenn bei Deposit würden sie wohl doppelt berechnet werden
            //transaction.setFee(Parse.stringToDoublePositive(entry[8]));
            //transaction.setFeeCurrency(entry[9]);
            transaction.setExchange(getName());
            if(entry.length >= 7)
                transaction.setComment(entry[6]);

            DateTimeFormatter formatter = null;
            LocalDateTime dateTime = null;

            try {
                formatter = DateTimeFormatter.ISO_DATE_TIME;
                dateTime = LocalDateTime.parse(entry[1], formatter);
            } catch (Exception ex) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                dateTime = LocalDateTime.parse(entry[1], formatter);
            }

            transaction.setDateTime(dateTime);
        }

        if (type == TransactionType.Deposit)
        {
            transaction.setType(type);
            transaction.setInValue(Parse.stringToDoublePositive(entry[5]));
            transaction.setInCurrency(entry[4]);
            //Transfer
            //Die Gebüren sollten nur beim senden anfallen, wenn bei Deposit würden sie wohl doppelt berechnet werden
            //transaction.setFee(Parse.stringToDoublePositive(entry[8]));
            //transaction.setFeeCurrency(entry[9]);
            transaction.setExchange(getName());
            if(entry.length >= 7)
                transaction.setComment(entry[6]);

            DateTimeFormatter formatter = null;
            LocalDateTime dateTime = null;

            try {
                formatter = DateTimeFormatter.ISO_DATE_TIME;
                dateTime = LocalDateTime.parse(entry[1], formatter);
            } catch (Exception ex) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                dateTime = LocalDateTime.parse(entry[1], formatter);
            }

            transaction.setDateTime(dateTime);
        }

        if (type == TransactionType.Fee)
        {
            transaction.setType(type);
            transaction.setFee(Parse.stringToDoublePositive(entry[5]));
            transaction.setFeeCurrency(entry[4]);
            //Transfer
            //Die Gebüren sollten nur beim senden anfallen, wenn bei Deposit würden sie wohl doppelt berechnet werden
            //transaction.setFee(Parse.stringToDoublePositive(entry[8]));
            //transaction.setFeeCurrency(entry[9]);
            transaction.setExchange(getName());
            if(entry.length >= 7)
                transaction.setComment(entry[6]);

            DateTimeFormatter formatter = null;
            LocalDateTime dateTime = null;

            try {
                formatter = DateTimeFormatter.ISO_DATE_TIME;
                dateTime = LocalDateTime.parse(entry[1], formatter);
            } catch (Exception ex) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                dateTime = LocalDateTime.parse(entry[1], formatter);
            }

            transaction.setDateTime(dateTime);
        }

        if(type == TransactionType.Trade){
            transaction.setType(TransactionType.Trade);
            transaction.setOutValue(Parse.stringToDoublePositive(entry[5]));
            transaction.setOutCurrency(entry[4]);
            //Transfer
            //Die Gebüren sollten nur beim senden anfallen, wenn bei Deposit würden sie wohl doppelt berechnet werden
            transaction.setFee(Parse.stringToDoublePositive(entry[8]));
            transaction.setFeeCurrency(entry[9]);
            transaction.setInValue(Parse.stringToDoublePositive(entry[10]));
            transaction.setInCurrency(entry[11]);
            transaction.setExchange(getName());
            if(entry.length >= 7)
                transaction.setComment(entry[6]);

            DateTimeFormatter formatter = null;
            LocalDateTime dateTime = null;

            try {
                formatter = DateTimeFormatter.ISO_DATE_TIME;
                dateTime = LocalDateTime.parse(entry[1], formatter);
            } catch (Exception ex) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                dateTime = LocalDateTime.parse(entry[1], formatter);
            }

            transaction.setDateTime(dateTime);
        }
        return transaction;
    }

    @Override
    public String getName(){
        return "Binance";
    }

}
