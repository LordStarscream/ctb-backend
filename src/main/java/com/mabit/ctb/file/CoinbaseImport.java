package com.mabit.CTB.fileImport;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import com.mabit.CTB.entity.Currency;
import com.mabit.CTB.entity.Location;
import com.mabit.CTB.entity.TransactionImport;
import com.mabit.CTB.enums.TransactionType;
import com.mabit.CTB.helper.Parse;
import com.mabit.CTB.repository.CurrencyRepository;
import com.mabit.CTB.repository.LocationRepository;
import com.mabit.CTB.repository.TransactionImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
@Service
@Qualifier("Coinbase")
public class CoinbaseImport implements FileImport{

    @Autowired
    private CsvReader csvReader;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private TransactionImportRepository importRepository;

    private Dictionary<String, TransactionType> typeMapping;
    private Dictionary<String, TransactionType> tradeDirection;

    public CoinbaseImport() {
    }

    private List<TransactionImport> importEntities;

    public List<TransactionImport> getImportEntities() {
        return importEntities;
    }

    private void initDictionaries() {
        typeMapping = new Hashtable<String, TransactionType>();
        typeMapping.put("Buy", TransactionType.Trade);
        typeMapping.put("Sell", TransactionType.Trade);
        typeMapping.put("Rewards Income", TransactionType.Income);
        typeMapping.put("Learning Reward", TransactionType.Gift);
        typeMapping.put("Coinbase Earn", TransactionType.Gift);
        typeMapping.put("Receive", TransactionType.Deposit);
        typeMapping.put("Send", TransactionType.Withdraw);

        tradeDirection = new Hashtable<String, TransactionType>();
        tradeDirection.put("Buy", TransactionType.Deposit);
        tradeDirection.put("Sell", TransactionType.Withdraw);

    }

    private TransactionImport entryToEntity(String[] entry) {
        TransactionImport transaction = new TransactionImport();
        var type = typeMapping.get(entry[1]);
        if (type == TransactionType.Trade){
            var direction = tradeDirection.get(entry[1]);
            if (direction == TransactionType.Deposit){
                transaction.setType(type);
                transaction.setInValue(Parse.StringToDouble(entry[3]));
                transaction.setInCurrency(entry[2]);
                transaction.setOutValue(- Parse.StringToDouble(entry[6]));
                transaction.setOutCurrency(entry[4]);
            }else{
                transaction.setType(type);
                transaction.setInValue(Parse.StringToDouble(entry[6]));
                transaction.setInCurrency(entry[4]);
                transaction.setOutValue(Parse.StringToDouble(entry[3]));
                transaction.setOutCurrency(entry[2]);
            }
        }
        if (type == TransactionType.Gift || type == TransactionType.Deposit || type == TransactionType.Income){
            transaction.setType(type);
            transaction.setInValue(Parse.StringToDouble(entry[3]));
            transaction.setInCurrency(entry[2]);
            transaction.setOutValue(Parse.StringToDouble(entry[6]));
            transaction.setOutCurrency(entry[4]);
        }

        if(type == TransactionType.Withdraw){
            transaction.setType(type);
                transaction.setInValue(Parse.StringToDouble(entry[6]));
                transaction.setInCurrency(entry[4]);
                transaction.setOutValue(Parse.StringToDouble(entry[3]));
                transaction.setOutCurrency(entry[2]);
        }
        transaction.setFee(Parse.StringToDouble(entry[8]));
        transaction.setFeeCurrency(entry[4]);
        transaction.setExchange(getName());
        transaction.setComment(entry[9]);

        //alternativ : 2019-11-22T08:06:50.400Z
        DateTimeFormatter formatter = null;
        LocalDateTime dateTime = null;
        try {
            try {
                formatter = DateTimeFormatter.ISO_DATE_TIME;
                dateTime = LocalDateTime.parse(entry[0], formatter);
            } catch (Exception ex) {
                formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
                dateTime = LocalDateTime.parse(entry[0], formatter);
            }
        } catch (Exception ex) {
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
        initDictionaries();
        csvReader.setStringDelimiter("\"");
        var test = csvReader.getStringDelimiter();
        csvReader.Import(file);
        /* only for generating new Template of import
        String[] header = csvReader.getHeader();*/
        ArrayList<String[]> entries = csvReader.getEntries();
        importEntities = convertToEntities(entries);
        persistImport();
        System.out.println("Import Successfull");
    }

    @Override
    public String getName(){
        return "Coinbase";
    }

    @Override
    public String toString(){
        return getName();
    }


}
