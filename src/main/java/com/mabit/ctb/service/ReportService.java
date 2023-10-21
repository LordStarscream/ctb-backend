package com.mabit.ctb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabit.ctb.repository.TransactionRepository;

@Service
public class ReportService {

    @Autowired
    TransactionRepository transactionRepository;

    public List<Integer> availableReportYear() {
        List<Integer> existingTradeYears = new ArrayList<>();
        var all = transactionRepository.findAll();
        for(var dt: all){
            if (!existingTradeYears.contains(dt.getDateTime().getYear())){
                existingTradeYears.add(dt.getDateTime().getYear());
            }
        }
        /*
        TODO wenn eine Tabelle für die Reports exitiert,
        dann hier mit den jahren abgleichen, wenn nicht existiert
        neuen eintrag anlegen
        */
        return existingTradeYears;
    }
}
