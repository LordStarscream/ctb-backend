package com.mabit.ctb.file;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

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
@Qualifier("Coinbase")
public class CoinbaseImport extends FileImport{

    public List<TransactionImport> getImportEntities() {
        return importEntities;
    }

    private HashMap<String, TransactionType> typeMap() {
        HashMap<String, TransactionType> typeMapping = new HashMap<>();
        typeMapping.put("Buy", TransactionType.Trade);
        typeMapping.put("Sell", TransactionType.Trade);
        typeMapping.put("Rewards Income", TransactionType.Income);
        typeMapping.put("Learning Reward", TransactionType.Gift);
        typeMapping.put("Coinbase Earn", TransactionType.Gift);
        typeMapping.put("Receive", TransactionType.Deposit);
        typeMapping.put("Send", TransactionType.Withdraw);
        return typeMapping;

    }

    private HashMap<String, TransactionType> tradeDirectionMap() {
        HashMap<String, TransactionType> tradeDirection = new HashMap<>();
        tradeDirection.put("Buy", TransactionType.Deposit);
        tradeDirection.put("Sell", TransactionType.Withdraw);
        return tradeDirection;
    }

    @Override
    protected TransactionImport entryToEntity(String[] entry) {
        var typeMapping = typeMap();
        var tradeDirection = tradeDirectionMap();
        TransactionImport transaction = new TransactionImport();
        var type = typeMapping.get(entry[1]);
        if (type == TransactionType.Trade){
            var direction = tradeDirection.get(entry[1]);
            if (direction == TransactionType.Deposit){
                transaction.setType(type);
                transaction.setInValue(Parse.stringToDouble(entry[3]));
                transaction.setInCurrency(entry[2]);
                transaction.setOutValue(Parse.stringToDouble(entry[6]));
                transaction.setOutCurrency(entry[4]);
                transaction.setInRate(Parse.stringToDouble(entry[5]));
            }else{
                transaction.setType(type);
                transaction.setInValue(Parse.stringToDouble(entry[6]));
                transaction.setInCurrency(entry[4]);
                transaction.setOutValue(Parse.stringToDouble(entry[3]));
                transaction.setOutCurrency(entry[2]);
                transaction.setOutRate(Parse.stringToDouble(entry[5]));
            }
        }
        if (type == TransactionType.Gift || type == TransactionType.Deposit || type == TransactionType.Income){
            transaction.setType(type);
            transaction.setInValue(Parse.stringToDouble(entry[3]));
            transaction.setInCurrency(entry[2]);
            transaction.setOutValue(Parse.stringToDouble(entry[6]));
            transaction.setOutCurrency(entry[4]);
            transaction.setInRate(Parse.stringToDouble(entry[5]));
        }

        if(type == TransactionType.Withdraw){
            transaction.setType(type);
                transaction.setInValue(Parse.stringToDouble(entry[6]));
                transaction.setInCurrency(entry[4]);
                transaction.setOutValue(Parse.stringToDouble(entry[3]));
                transaction.setOutCurrency(entry[2]);
                transaction.setOutRate(Parse.stringToDouble(entry[5]));
        }
        transaction.setFee(Parse.stringToDouble(entry[8]));
        transaction.setFeeCurrency(entry[4]);
        transaction.setExchange(getName());
        transaction.setComment(entry[9]);

        //alternativ : 2019-11-22T08:06:50.400Z
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

        return transaction;
    }

    @Override
    public String getName(){
        return "Coinbase";
    }
}
