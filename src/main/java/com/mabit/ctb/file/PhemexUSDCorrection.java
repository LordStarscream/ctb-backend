package com.mabit.ctb.file;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.mabit.ctb.entity.TransactionImport;
import com.mabit.ctb.types.TransactionType;
import com.mabit.ctb.repository.TransactionImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
@Service
@Qualifier("PhemexUSDCorrection")
public class PhemexUSDCorrection implements FileImport{

    @Autowired
    private CsvReader csvReader;
    @Autowired
    private TransactionImportRepository importRepository;
    private List<TransactionImport> importEntities;


    public List<TransactionImport> getImportEntities() {
        return importEntities;
    }

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

    private TransactionImport entryToEntity(String[] entry) {
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
            try {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                dateTime = LocalDateTime.parse(entry[0], formatter);
            } catch (Exception ex) {
                formatter = DateTimeFormatter.ISO_DATE_TIME;
                dateTime = LocalDateTime.parse(entry[0], formatter);
            }
        } catch (Exception ex) {
        }
        transaction.setDateTime(dateTime);
        transaction.setFee(0.00);
        transaction.setFeeCurrency("");

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
        return "Phemex USD Correction";
    }

    @Override
    public String toString(){
        return getName();
    }
}
