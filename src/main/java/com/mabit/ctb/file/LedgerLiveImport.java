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
import com.mabit.ctb.repository.CurrencyRepository;
import com.mabit.ctb.repository.LocationRepository;
import com.mabit.ctb.repository.TransactionImportRepository;
import com.mabit.ctb.types.TransactionType;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
@Slf4j
@Service
@Qualifier("LedgerLive")
public class LedgerLiveImport extends FileImport{

    private Dictionary<String, TransactionType> typeMapping;

    public LedgerLiveImport() {
        initDictionaries();
    }

    private void initDictionaries() {
        typeMapping = new Hashtable<String, TransactionType>();
        typeMapping.put("IN", TransactionType.Deposit);
        typeMapping.put("OUT", TransactionType.Withdraw);
        typeMapping.put("FEES", TransactionType.Withdraw);

    }

    @Override
    protected TransactionImport entryToEntity(String[] entry) {
        TransactionImport transaction = new TransactionImport();
        var type = typeMapping.get(entry[2]);
        boolean isFee = (entry[2].equals("FEES"));
        /*
            if (type == TransactionType.Deposit){
                as buy is done in 3rd party no need here
                transaction.setInValue(Parse.StringToDouble(entry[3]));
                transaction.setInCurrency(entry[1]);
            }*/
        if (type == TransactionType.Deposit)
            return null;
        if (type == TransactionType.Withdraw)
        {
            transaction.setType(TransactionType.Trade);
            transaction.setOutValue(Parse.stringToDouble(entry[3]));
            transaction.setOutCurrency(entry[1]);
            transaction.setInValue(Parse.stringToDouble(entry[9]));
            transaction.setInCurrency(entry[8]);
            //Die Gebüren sollten nur beim senden anfallen, wenn bei Deposit würden sie wohl doppelt berechnet werden
            if(isFee){
                transaction.setFee(Parse.stringToDouble(entry[4]));
                transaction.setFeeCurrency(entry[1]);
            }
            transaction.setExchange(getName());
            transaction.setComment(entry[6]);

            DateTimeFormatter formatter = null;
            LocalDateTime dateTime = null;
            try {
                formatter = DateTimeFormatter.ISO_DATE_TIME;
                dateTime = LocalDateTime.parse(entry[0], formatter);
            } catch (Exception ex) {
                log.info("Casting into ISO_DATA_TIME not possible, take dd.MM.yy HH:mm instead");
                formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
                dateTime = LocalDateTime.parse(entry[0], formatter);
            }

            transaction.setDateTime(dateTime);
        }
        return transaction;
    }


    @Override
    public String getName(){
        return "LedgerLive";
    }

}
