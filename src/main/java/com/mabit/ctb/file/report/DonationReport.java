package com.mabit.ctb.file.report;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mabit.ctb.entity.report.Donation;
import com.mabit.ctb.file.Representation;

@Component
public class DonationReport {
    private List<Donation> donations;

    public DonationReport(List<Donation> donations){
        this.donations = donations;
    }

    private String[] getHeader(){
        String[] header = new String[8];
        header[0] = "Datum";
        header[1] = "Anzahl";
        header[2] = "Währung";
        header[3] = "Type";
        header[4] = "Ausgang bei";
        header[5] = "Kostenbasis Berechnung";
        header[6] = "Kostenbasis in EUR";
        header[7] = "Wert bei Auszahlung in EUR";

        return header;
    }

    private String[] toEntry(Donation donation){
        String[] entries = new String[8];
        entries[0] = donation.getOutDateTime().format(DateTimeFormatter.ISO_DATE);
        entries[1] = Representation.getCryptoFormat(donation.getAmmount());
        entries[2] = donation.getCurrency().getTicker();
        entries[3] = donation.getType().toString();
        entries[4] = donation.getOutAt().getName();
        entries[5] = donation.getCostBaseCalculation();
        entries[6] = Representation.getPriceFormat(donation.getCostBase());
        entries[7] = Representation.getPriceFormat(donation.getWorthAtOut());

        return entries;
    }

    public byte[] export() {
        List<String[]> csvEntries = new ArrayList<>();
        var header = getHeader();
        csvEntries.add(header);
        this.donations.forEach(in -> {
            csvEntries.add(toEntry(in));
        });
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.setStringDelimiter("\"");
        csvWriter.setEntries(csvEntries);
        return csvWriter.export();
    }

}
