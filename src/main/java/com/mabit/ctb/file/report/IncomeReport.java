package com.mabit.ctb.file.report;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import com.mabit.ctb.entity.report.Income;
import com.mabit.ctb.file.Representation;

public class IncomeReport {
     private List<Income> incomes;

    public IncomeReport(List<Income> incomes){
        this.incomes = incomes;
    }

    private String[] getHeader(){
        String[] header = new String[7];
        header[0] = "Datum des Eingangs";
        header[1] = "Anzahl";
        header[2] = "Währung";
        header[3] = "Type";
        header[4] = "Eingang bei";
        header[5] = "Kommentar / Hinweis";
        header[6] = "Wert bei Eingang in EUR";

        return header;
    }

    private String[] toEntry(Income income){
        String[] entries = new String[7];
        entries[0] = income.getInDateTime().format(DateTimeFormatter.ISO_DATE);
        entries[1] = Representation.getCryptoFormat(income.getAmmount());
        entries[2] = income.getCurrency().getTicker();
        entries[3] = income.getType().toString();
        entries[4] = income.getInAt().getName();
        entries[5] = income.getInfo();
        entries[6] = Representation.getPriceFormat(income.getWorthAtIncome());

        return entries;
    }

    public byte[] export() {
        List<String[]> csvEntries = new ArrayList<>();
        var header = getHeader();
        csvEntries.add(header);
        this.incomes.forEach(in -> {
            csvEntries.add(toEntry(in));
        });
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.setStringDelimiter("\"");
        csvWriter.setEntries(csvEntries);
        return csvWriter.export();
    }

}
