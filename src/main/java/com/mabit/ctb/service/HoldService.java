package com.mabit.ctb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.Transaction;
import com.mabit.ctb.entity.report.Hold;
import com.mabit.ctb.repository.HoldRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HoldService {

    @Autowired
    private HoldRepository holdRepository;

    public void addToHoldings(Transaction transaction) {
        log.debug("addToHoldings: {}",transaction.getInCurrency().getTicker());
        log.debug("Before Holding of {}", getListOfHoldings(transaction.getInCurrency()));
        addHold(new Hold(transaction.getInValue(), transaction.getInCurrency(), transaction.getDateTime(),
            transaction.getExchange(), transaction.getInFiatExchange().getFactor(),null));
        log.debug("After  Holding: {} ",getListOfHoldings(transaction.getInCurrency()));
    }

    public void addHold(Hold hold){
        holdRepository.save(hold);
    }

    public List<Hold> getHoldings(Currency currency){
        return holdRepository.findByInCurrency(currency);
    }

    private String getListOfHoldings(Currency currency){
        List<Hold> list = holdRepository.findByInCurrency(currency);
        Double sum = 0.0;
        StringBuilder sb = new StringBuilder();
        sb.append(currency.getTicker()).append(": ");
        for(Hold h:list){
            sum = sum+h.getAmmount();
            sb.append("[").append(h.getAmmount()).append("]");
        }
        sb.append(":: ").append(sum);
        return sb.toString();
    }

    /*
     * service for holdings sortiert nach currency wie im alten design für die auswertung
     */

}
