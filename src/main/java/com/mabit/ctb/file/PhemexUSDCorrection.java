package com.mabit.ctb.file;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.mabit.ctb.entity.TransactionImport;
import com.mabit.ctb.types.TransactionType;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
@Slf4j
@Service
@Qualifier("PhemexUSDCorrection")
public class PhemexUSDCorrection extends FileImport{

    private TransactionType getType(String type, String value){
        if (type.equals("Bonus"))
            return TransactionType.Gift;
        if ((type.equals("Überweisen")) && (value.startsWith("+")))
                return TransactionType.Deposit;
        return TransactionType.Withdraw;
    }

    private String getCurrency(String valueWithTicker) {
        var a = valueWithTicker.split(" ");
        return a[1];
    }

    private Double getValue(String valueWithTicker) {
        var value = valueWithTicker.substring(1); //remove + or -
        var a = value.split(" ");
        return Parse.stringToDouble(a[0]);
    }

    @Override
    protected TransactionImport entryToEntity(String[] entry) {
        TransactionImport transaction = new TransactionImport();
        var type = getType(entry[2],entry[3]);
        transaction.setType(getType(entry[2],entry[3]));
        transaction.setExchange("Phemex");
        if (type == TransactionType.Withdraw){
            transaction.setType(TransactionType.Trade);
            transaction.setOutValue(getValue(entry[3]));
            transaction.setOutCurrency(getCurrency(entry[3]));
            transaction.setInValue(getValue(entry[6]));
            transaction.setInCurrency(getCurrency(entry[6]));
        }
        else{
            transaction.setInValue(getValue(entry[3]));
            transaction.setInCurrency(getCurrency(entry[3]));
            if(type == TransactionType.Deposit){
                transaction.setType(TransactionType.Trade);
                transaction.setOutValue(getValue(entry[6]));
                transaction.setOutCurrency(getCurrency(entry[6]));
            }
        }

        //alternativ : 2019-11-22T08:06:50.400Z
        DateTimeFormatter formatter = null;
        LocalDateTime dateTime = null;

        try {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTime = LocalDateTime.parse(entry[0], formatter);
        } catch (Exception ex) {
            log.info("Casting into defined format not possible, take ISO_DATA_TIME instead");
            formatter = DateTimeFormatter.ISO_DATE_TIME;
            dateTime = LocalDateTime.parse(entry[0], formatter);
        }

        transaction.setDateTime(dateTime);
        transaction.setFee(0.00);
        transaction.setFeeCurrency("");

        return transaction;
    }

    @Override
    public String getName(){
        return "Phemex USD Correction";
    }

}
