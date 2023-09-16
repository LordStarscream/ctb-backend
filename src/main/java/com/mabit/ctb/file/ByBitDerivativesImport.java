package com.mabit.ctb.file;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.mabit.ctb.entity.TransactionImport;
import com.mabit.ctb.types.TransactionType;
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
@Qualifier("Bybit_Derivatives")
public class ByBitDerivativesImport implements FileImport{

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

    private String getCurrency(String value){
        return value.substring(0,3);
    }

    private Double getGuvValue(String guv){
        var val = guv;
        if (guv.startsWith("-"))
            val = guv.substring(1);
        return Parse.stringToDouble(val);
    }

    private TransactionImport entryToEntity(String[] entry) {
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
            formatter = DateTimeFormatter.ISO_DATE_TIME;
            dateTime = LocalDateTime.parse(timeField, formatter);
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
        return "Bybit PNL";
    }

    @Override
    public String toString(){
        return getName();
    }


}
