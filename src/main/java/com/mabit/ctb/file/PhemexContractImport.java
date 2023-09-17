package com.mabit.ctb.file;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
@Qualifier("Phemex_Contract")
public class PhemexContractImport extends FileImport {

    public List<TransactionImport> getImportEntities() {
        return importEntities;
    }

    private TransactionType getType(String guv) { // gewinn und verlust
        if (guv.startsWith("-"))
            return TransactionType.Withdraw;
        return TransactionType.Deposit;
    }

    private String removeCurrencyFromValue(String value) {
        return value.split("([A-Z]+)")[0];
    }

    private String getCurrencyFromValue(String value) {
        var val = removeCurrencyFromValue(value);
        return value.replaceFirst(val, "");
    }

    private Double getGuvValue(String guv) {
        var valString = removeCurrencyFromValue(guv);
        if (guv.startsWith("-"))
            valString = valString.substring(1);
        return Parse.stringToDouble(valString);
    }

    @Override
    protected TransactionImport entryToEntity(String[] entry) {
        TransactionImport transaction = new TransactionImport();
        var guv = entry[9];
        var type = getType(guv);
        transaction.setType(type);
        if (type == TransactionType.Deposit) {
            transaction.setInValue(getGuvValue(guv));
            transaction.setInCurrency(getCurrencyFromValue(guv));
        } else {
            transaction.setOutValue(getGuvValue(guv));
            transaction.setOutCurrency(getCurrencyFromValue(guv));
        }
        transaction.setFee(Parse.stringToDouble(removeCurrencyFromValue(entry[7]))
                - Parse.stringToDouble(removeCurrencyFromValue(entry[8])));
        transaction.setFeeCurrency(getCurrencyFromValue(entry[7]));
        transaction.setExchange("Phemex");
        transaction.setComment(entry[2]);

        // alternativ : 2019-11-22T08:06:50.400Z
        DateTimeFormatter formatter = null;
        LocalDateTime dateTime = null;

        try {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTime = LocalDateTime.parse(entry[1], formatter);
        } catch (Exception ex) {
            log.info("Casting into defined format not possible, take ISO_DATA_TIME instead");
            formatter = DateTimeFormatter.ISO_DATE_TIME;
            dateTime = LocalDateTime.parse(entry[1], formatter);
        }
        transaction.setDateTime(dateTime);
        return transaction;
    }

    @Override
    public String getName() {
        return "Phemex GuV";
    }
}
