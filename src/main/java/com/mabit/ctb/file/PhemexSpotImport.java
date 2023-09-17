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
@Qualifier("Phemex_Spot")
public class PhemexSpotImport extends FileImport{

    private String getCurrency(String valueWithTicker) {
        var a = valueWithTicker.split(" ");
        return a[1];
    }

    private Double getValue(String valueWithTicker) {
        var a = valueWithTicker.split(" ");
        return Parse.stringToDouble(a[0]);
    }

    @Override
    protected TransactionImport entryToEntity(String[] entry) {
        TransactionImport transaction = new TransactionImport();
        transaction.setType(TransactionType.Trade);
        transaction.setInValue(getValue(entry[3]));
        transaction.setInCurrency(getCurrency(entry[3]));
        transaction.setOutValue(getValue(entry[5]));
        transaction.setOutCurrency(getCurrency(entry[5]));
        transaction.setFee(getValue(entry[9]));
        transaction.setFeeCurrency(getCurrency(entry[9]));
        transaction.setExchange("Phemex");

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

        return transaction;
    }

    @Override
    public String getName(){
        return "Phemex Spot";
    }
}
