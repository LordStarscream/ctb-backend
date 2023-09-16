package com.mabit.ctb.file;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.Location;
import com.mabit.ctb.entity.TransactionImport;
import com.mabit.ctb.types.TransactionType;
import com.mabit.ctb.repository.CurrencyRepository;
import com.mabit.ctb.repository.LocationRepository;
import com.mabit.ctb.repository.TransactionImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
@Service
@Qualifier("Phemex_Contract")
public class PhemexContractImport implements FileImport{

    @Autowired
    private CsvReader csvReader;
    @Autowired
    private TransactionImportRepository importRepository;
    private List<TransactionImport> importEntities;

    public List<TransactionImport> getImportEntities() {
        return importEntities;
    }

    private TransactionType getType(String guv) { //gewin und verlust
        if (guv.startsWith("-"))
            return TransactionType.Withdraw;
        return TransactionType.Deposit;
    }

    private String removeCurrencyFromValue(String value){
        return value.split("([A-Z]+)")[0];
    }

    private String getCurrencyFromValue(String value){
        var val = removeCurrencyFromValue(value);
        return value.replaceFirst(val, "");
    }

    private Double getGuvValue(String guv){
        var valString = removeCurrencyFromValue(guv);
        if (guv.startsWith("-"))
            valString = valString.substring(1);
        return Parse.stringToDouble(valString);
    }

    private TransactionImport entryToEntity(String[] entry) {
        TransactionImport transaction = new TransactionImport();
        var guv = entry[9];
        var type = getType(guv);
        transaction.setType(type);
        if (type == TransactionType.Deposit){
            transaction.setInValue(getGuvValue(guv));
            transaction.setInCurrency(getCurrencyFromValue(guv));
        }
        else
        {
            transaction.setOutValue(getGuvValue(guv));
            transaction.setOutCurrency(getCurrencyFromValue(guv));
        }
        transaction.setFee(Parse.stringToDouble(removeCurrencyFromValue(entry[7]))-Parse.stringToDouble(removeCurrencyFromValue(entry[8])));
        transaction.setFeeCurrency(getCurrencyFromValue(entry[7]));
        transaction.setExchange("Phemex");
        transaction.setComment(entry[2]);

        //alternativ : 2019-11-22T08:06:50.400Z
        DateTimeFormatter formatter = null;
        LocalDateTime dateTime = null;

        try {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTime = LocalDateTime.parse(entry[1], formatter);
        } catch (Exception ex) {
            formatter = DateTimeFormatter.ISO_DATE_TIME;
            dateTime = LocalDateTime.parse(entry[1], formatter);
        }
        transaction.setDateTime(dateTime);
        return transaction;
    }

    private List<TransactionImport> convertToEntities(ArrayList<String[]> entries) {
        List<TransactionImport> transactions = new ArrayList<>();
        for (String[] entry : entries) {
            transactions.add(entryToEntity(entry));
        }
        return transactions;
    }

    private void persistImport() {
        if (!this.importEntities.isEmpty()) {
            importRepository.saveAll(this.importEntities);
        }
    }

    @Override
    public void importFile(File file) {
        csvReader.setStringDelimiter("\"");
        var test = csvReader.getStringDelimiter();
        csvReader.Import(file);
        ArrayList<String[]> entries = csvReader.getEntries();
        importEntities = convertToEntities(entries);
        persistImport();
        System.out.println("Import Successfull");
    }

    @Override
    public String getName(){
        return "Phemex GuV";
    }

    @Override
    public String toString(){
        return getName();
    }


}
