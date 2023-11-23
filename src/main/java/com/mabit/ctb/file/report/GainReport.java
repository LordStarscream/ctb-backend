package com.mabit.ctb.file.report;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import com.mabit.ctb.entity.report.Gain;
import com.mabit.ctb.file.Representation;

public class GainReport {
    private List<Gain> gains;

    public GainReport(List<Gain> gains){
        this.gains = gains;
    }

    private String[] getHeader(){
        String[] header = new String[11];
        header[0] = "Anzahl";
        header[1] = "Währung";
        header[2] = "Erwerbsdatum";
        header[3] = "Verkaufsdatum";
        header[4] = "Short/Long";
        header[5] = "Kauf/Eingang bei";
        header[6] = "Verkauf/Ausgang bei";
        header[7] = "Type";
        header[8] = "Kostenbasis in EUR";
        header[9] = "Erlös in EUR";
        header[10] = "Gewinn/Verlust in EUR";
        return header;
    }

    private String[] toEntry(Gain gain){
        String[] entries = new String[11];
        entries[0] = Representation.getCryptoFormat(gain.getAmmount());
        entries[1] = gain.getCurrency().getTicker();
        entries[2] = gain.getInDateTime().format(DateTimeFormatter.ISO_DATE);
        entries[3] = gain.getOutDateTime().format(DateTimeFormatter.ISO_DATE);
        entries[4] = gain.getShortLong();
        entries[5] = gain.getBuyAt().getName();
        entries[6] = gain.getSellAt().getName();
        entries[7] = "Trade";
        entries[8] = Representation.getPriceFormat(gain.getCostbasis());
        entries[9] = Representation.getPriceFormat(gain.getProceeds());
        try{
        entries[10] = Representation.getPriceFormat(Representation.getValueFromPriceFormat(entries[9])-Representation.getValueFromPriceFormat(entries[8]));
        }catch (ParseException e){
            entries[10] = null;
        }
        //entries[10] = Representation.getPriceFormat(Double.valueOf(entries[9].replace(",", "."))-Double.valueOf(entries[8].replace(",", ".")));
        return entries;
    }

    public byte[] export() {
        List<String[]> csvEntries = new ArrayList<>();
        var header = getHeader();
        csvEntries.add(header);
        this.gains.forEach(in -> {
            csvEntries.add(toEntry(in));
        });
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.setStringDelimiter("\"");
        csvWriter.setEntries(csvEntries);
        return csvWriter.export();
    }
}
