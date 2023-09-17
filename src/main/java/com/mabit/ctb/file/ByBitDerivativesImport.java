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
@Qualifier("Bybit_Derivatives")
public class ByBitDerivativesImport extends FileImport{

    private TransactionType getType(String guv) { //gewin und verlust
        if (guv.startsWith("-"))
            return TransactionType.Withdraw;
        return TransactionType.Deposit;
    }

    private String getCurrency(String value){
        return value.substring(0,3);
    }

    private Double getGuvValue(String guv){
        var val = guv;
        if (guv.startsWith("-"))
            val = guv.substring(1);
        return Parse.stringToDouble(val);
    }

    @Override
    protected TransactionImport entryToEntity(String[] entry) {
        TransactionImport transaction = new TransactionImport();
        var guv = entry[5];
        var contracts = entry[0];
        var type = getType(guv);
        var timeField = entry[7];
        transaction.setType(type);
        if (type == TransactionType.Deposit){
            transaction.setInValue(getGuvValue(guv));
            transaction.setInCurrency(getCurrency(contracts));
        }
        else
        {
            transaction.setOutValue(getGuvValue(guv));
            transaction.setOutCurrency(getCurrency(contracts));
        }
        transaction.setFee(0.00);
        transaction.setFeeCurrency(getCurrency(contracts));
        transaction.setExchange("ByBit");
        transaction.setComment(entry[1]);

        //alternativ : 2019-11-22T08:06:50.400Z
        DateTimeFormatter formatter = null;
        LocalDateTime dateTime = null;

        try {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTime = LocalDateTime.parse(timeField, formatter);
        } catch (Exception ex) {
            log.info("Casting into defined format not possible, take ISO_DATA_TIME instead");
            formatter = DateTimeFormatter.ISO_DATE_TIME;
            dateTime = LocalDateTime.parse(timeField, formatter);
        }

        transaction.setDateTime(dateTime);
        return transaction;
    }

    @Override
    public String getName(){
        return "Bybit PNL";
    }

}
