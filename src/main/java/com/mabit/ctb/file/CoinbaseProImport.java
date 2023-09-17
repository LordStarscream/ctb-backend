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
@Qualifier("CoinbasePro")
public class CoinbaseProImport extends FileImport{

    public List<TransactionImport> getImportEntities() {
        return importEntities;
    }

    private HashMap<String, TransactionType> typeMap() {
        HashMap<String, TransactionType> typeMapping = new HashMap<>();
        typeMapping.put("BUY", TransactionType.Deposit);
        typeMapping.put("SELL", TransactionType.Withdraw);
        return typeMapping;
    }

    @Override
    protected TransactionImport entryToEntity(String[] entry) {
        var typeMapping = typeMap();
        TransactionImport transaction = new TransactionImport();
        var type = typeMapping.get(entry[3]);
        transaction.setType(TransactionType.Trade);
        if (type == TransactionType.Deposit){
            transaction.setInValue(Parse.stringToDouble(entry[5]));
            transaction.setInCurrency(entry[6]);
            transaction.setOutValue(- Parse.stringToDouble(entry[9]));
            transaction.setOutCurrency(entry[10]);
        }
        else
        {
            transaction.setInValue(Parse.stringToDouble(entry[9]));
            transaction.setInCurrency(entry[10]);
            transaction.setOutValue(Parse.stringToDouble(entry[5]));
            transaction.setOutCurrency(entry[6]);
        }
        transaction.setFee(Parse.stringToDouble(entry[8]));
        transaction.setFeeCurrency(entry[10]);
        transaction.setExchange(getName());
        transaction.setComment(entry[0]);

        //alternativ : 2019-11-22T08:06:50.400Z
        DateTimeFormatter formatter = null;
        LocalDateTime dateTime = null;

        try {
            formatter = DateTimeFormatter.ISO_DATE_TIME;
            dateTime = LocalDateTime.parse(entry[4], formatter);
        } catch (Exception ex) {
            log.info("Casting into ISO_DATA_TIME not possible, take dd.MM.yy HH:mm instead");
            formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
            dateTime = LocalDateTime.parse(entry[4], formatter);
        }
        transaction.setDateTime(dateTime);
        return transaction;
    }

    @Override
    public String getName(){
        return "Coinbase Pro";
    }
}
