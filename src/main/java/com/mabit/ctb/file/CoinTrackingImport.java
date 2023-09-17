package com.mabit.ctb.file;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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
@Qualifier("CoinTracking")
public class CoinTrackingImport extends FileImport{

    private HashMap<String, TransactionType> typeMap() {
        HashMap<String, TransactionType> typeMapping = new HashMap<>();
        typeMapping.put("Trade", TransactionType.Trade);
        typeMapping.put("Einzahlung", TransactionType.Deposit);
        typeMapping.put("Auszahlung", TransactionType.Withdraw);
        typeMapping.put("Geschenk", TransactionType.Gift);
        typeMapping.put("Einnahme", TransactionType.Income);
        typeMapping.put("Schenkung", TransactionType.Donation);
        typeMapping.put("Verloren", TransactionType.Lost); // TODO not shure if correct term for coinTracker
        return typeMapping;
    }

    @Override
    protected TransactionImport entryToEntity(String[] entry) {
        var typeMapping = typeMap();
        TransactionImport transaction = new TransactionImport();
        transaction.setType(typeMapping.get(entry[0]));
        transaction.setInValue(Parse.stringToDouble(entry[1]));
        transaction.setInCurrency(entry[2]);
        transaction.setOutValue(Parse.stringToDouble(entry[3]));
        transaction.setOutCurrency(entry[4]);
        transaction.setFee(Parse.stringToDouble(entry[5]));
        transaction.setFeeCurrency(entry[6]);
        transaction.setExchange(entry[7]);
        transaction.setComment(entry[9]);

        //alternativ : 2019-11-22T08:06:50.400Z
        DateTimeFormatter formatter = null;
        LocalDateTime dateTime = null;

        try {
            formatter = DateTimeFormatter.ISO_DATE_TIME;
            dateTime = LocalDateTime.parse(entry[10].replace(" ", "T"), formatter);
        } catch (Exception ex) {
            log.info("Casting ISO_DATA_TIME format not possible,take dd.MM.yy HH:mm instead");
            formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
            dateTime = LocalDateTime.parse(entry[10], formatter);
        }
        transaction.setDateTime(dateTime);

        return transaction;
    }

    @Override
    public String getName(){
        return "CoinTracking";
    }

}
