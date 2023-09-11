package com.mabit.CTB.fileImport;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.validation.Valid;

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
@Qualifier("Phemex_Spot")
public class PhemexSpotImport implements FileImport{

    @Autowired
    private CsvReader csvReader;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private TransactionImportRepository importRepository;

    private List<TransactionImport> importEntities;

    private Dictionary<String, TransactionType> typeMapping;

    public PhemexSpotImport(){
    }
    public List<TransactionImport> getImportEntities() {
        return importEntities;
    }

    private String getCurrency(String valueWithTicker) {
        var a = valueWithTicker.split(" ");
        return a[1];
    }

    private Double getValue(String valueWithTicker) {
        var a = valueWithTicker.split(" ");
        return Parse.StringToDouble(a[0]);
    }

    private Location getLocation(String name) {
        return locationRepository.findByName(name);
    }

    private void initDictionaries() {
        typeMapping = new Hashtable<String, TransactionType>();
        typeMapping.put("Buy", TransactionType.Deposit);
        typeMapping.put("Sell", TransactionType.Withdraw);

    }

    private TransactionImport entryToEntity(String[] entry) {
        TransactionImport transaction = new TransactionImport();
        var type = typeMapping.get(entry[3]);
        transaction.setType(TransactionType.Trade);
        transaction.setInValue(getValue(entry[3]));
        transaction.setInCurrency(getCurrency(entry[3]));
        transaction.setOutValue(getValue(entry[5]));
        transaction.setOutCurrency(getCurrency(entry[5]));
        transaction.setFee(getValue(entry[9]));
        transaction.setFeeCurrency(getCurrency(entry[9]));
        transaction.setExchange("Phemex");

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
        return "Phemex Spot";
    }

    @Override
    public String toString(){
        return getName();
    }


}
