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
@Qualifier("CoinTracking")
public class CoinTrackingImport implements FileImport{

    @Autowired
    private CsvReader csvReader;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private TransactionImportRepository importRepository;

    //private Dictionary<String, Currency> currencyMapping;
    private Dictionary<String, TransactionType> typeMapping;
    //private Dictionary<String, Location> locationMapping;

    public CoinTrackingImport() {
    }

    private List<TransactionImport> importEntities;

    public List<TransactionImport> getImportEntities() {
        return importEntities;
    }

    private Currency getCurrency(String ticker) {
        return currencyRepository.findByTicker(ticker);
    }

    private Location getLocation(String name) {
        return locationRepository.findByName(name);
    }

    private void initDictionaries() {
        typeMapping = new Hashtable<String, TransactionType>();
        typeMapping.put("Trade", TransactionType.Trade);
        typeMapping.put("Einzahlung", TransactionType.Deposit);
        typeMapping.put("Auszahlung", TransactionType.Withdraw);
        typeMapping.put("Geschenk", TransactionType.Gift);
        typeMapping.put("Einnahme", TransactionType.Income);
        typeMapping.put("Schenkung", TransactionType.Donation);
        typeMapping.put("Verloren", TransactionType.Lost); // TODO not shure if correct term for coinTracker
    }

    private TransactionImport entryToEntity(String[] entry) {
        TransactionImport transaction = new TransactionImport();
        transaction.setType(typeMapping.get(entry[0]));
        transaction.setInValue(Parse.StringToDouble(entry[1]));
        transaction.setInCurrency(entry[2]);
        transaction.setOutValue(Parse.StringToDouble(entry[3]));
        transaction.setOutCurrency(entry[4]);
        transaction.setFee(Parse.StringToDouble(entry[5]));
        transaction.setFeeCurrency(entry[6]);
        transaction.setExchange(entry[7]);
        transaction.setComment(entry[9]);

        //alternativ : 2019-11-22T08:06:50.400Z
        DateTimeFormatter formatter = null;
        LocalDateTime dateTime = null;
        try {
            try {
                formatter = DateTimeFormatter.ISO_DATE_TIME;
                dateTime = LocalDateTime.parse(entry[10].replace(" ", "T"), formatter);
            } catch (Exception ex) {
                formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
                dateTime = LocalDateTime.parse(entry[10], formatter);
            }
        } catch (Exception ex) {
        }
        transaction.setDateTime(dateTime);

        return transaction;
    }

    private List<TransactionImport> convertToEntities(ArrayList<String[]> entries) {
        List<TransactionImport> transactions = new ArrayList<TransactionImport>();
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
        return "CoinTracking";
    }

    @Override
    public String toString(){
        return getName();
    }

}
