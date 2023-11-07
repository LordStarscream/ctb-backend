package com.mabit.ctb.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.Transaction;
import com.mabit.ctb.entity.report.Deposit;
import com.mabit.ctb.entity.report.Donation;
import com.mabit.ctb.entity.report.Report;
import com.mabit.ctb.entity.report.Withdraw;
import com.mabit.ctb.file.Representation;
import com.mabit.ctb.repository.DepositRepository;
import com.mabit.ctb.repository.DonationRepository;
import com.mabit.ctb.repository.WithdrawRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HoldService {

    private static final Double ZERO_LIMIT = 0.0000000000003;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private WithdrawRepository withdrawRepository;

    @Autowired
    private DonationRepository donationRepository;

    public void addToHoldings(Transaction transaction) {
        log.debug("addToHoldings: {}",transaction.getInCurrency().getTicker());
        log.debug("Before Holding of {}", getListOfHoldings(transaction.getInCurrency()));
        deposit(new Deposit(transaction.getInValue(), transaction.getInCurrency(), transaction.getDateTime(),
            transaction.getExchange(), transaction.getInFiatExchange().getFactor()));
        log.debug("After  Holding: {} ",getListOfHoldings(transaction.getInCurrency()));
    }

    public void deposit(Deposit deposit){
        depositRepository.save(deposit);
    }

    public List<Deposit> getAvailableHoldings(Currency currency){
        return depositRepository.findByHasAvailableAmmountAndCurrency(true, currency);
    }

    public String getListOfHoldings(Currency currency){
        var list = depositRepository.findByHasAvailableAmmountAndCurrency(true, currency);
        Double sum = 0.0;
        StringBuilder sb = new StringBuilder();
        sb.append(currency.getTicker()).append(": ");
        for(Deposit deposit:list){
            sum = sum+deposit.getAvailableAmmount();
            sb.append("[").append(deposit.getAvailableAmmount()).append("]");
        }
        sb.append(":: ").append(sum);
        return sb.toString();
    }

    private Donation createDonation(Deposit deposit, Transaction transaction, Report report) {
        return createDonation(deposit,transaction, report, false);
    }

    private Donation createDonation(Deposit deposit, Transaction transaction, Report report, boolean useHoldValue) {
        var value = useHoldValue ? deposit.getAvailableAmmount() : transaction.getOutValue();
        var costbasis = value * deposit.getFactor();
        var costBaseText = value.toString()+" "+
                transaction.getOutCurrency().getTicker()+" @ "+Representation.getPriceFormat(deposit.getFactor())+
                " EUR \nAm "+deposit.getDateTime().format(DateTimeFormatter.ISO_DATE)+ " bei "+deposit.getLocation().getName();
        var fiatValue = value * transaction.getOutFiatExchange().getFactor();
        return new Donation(
                transaction.getOutValue(),
                transaction.getOutCurrency(),
                transaction.getDateTime(),
                costBaseText,
                costbasis,
                transaction.getExchange(),
                transaction.getType(),
                fiatValue,
                report);
    }

    public void addDonation(Transaction transaction, Report report) {
        //Report report = getReport(transaction);
        var currencyHold = getAvailableHoldings(transaction.getOutCurrency());
        var usedDeposits = new ArrayList<Deposit>();
        var donationValue = transaction.getOutValue();

        log.debug("donate At {}, with {} {} Value",transaction.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME),transaction.getFeeCurrency().getTicker(), transaction.getOutValue());
        //Print
        log.debug("Holding before donation: {}", getListOfHoldings(transaction.getOutCurrency()));
        for (Iterator<Deposit> cIter = currencyHold.iterator(); cIter.hasNext();) {
            Deposit deposit = cIter.next();
            usedDeposits.add(deposit);
            Double dif = deposit.getAvailableAmmount() - donationValue;
            log.debug("DIF=  {}", dif);
            if ((-ZERO_LIMIT <= dif) && (dif <= ZERO_LIMIT)){
                var withdraw = new Withdraw(donationValue,transaction.getOutCurrency(),transaction.getDateTime(), transaction.getExchange(),report, usedDeposits);
                withdrawRepository.save(withdraw);
                donationRepository.save(createDonation(deposit, transaction, report));
                cIter.remove();
                break;
            }
            if (dif > ZERO_LIMIT) {
                var withdraw = new Withdraw(donationValue,transaction.getOutCurrency(),transaction.getDateTime(), transaction.getExchange(),report, usedDeposits);
                withdrawRepository.save(withdraw);
                donationRepository.save(createDonation(deposit, transaction, report));
                deposit.setAvailableAmmount(dif);
                depositRepository.save(deposit);
                break;
            }
            if (dif < -ZERO_LIMIT) {
                donationRepository.save(createDonation(deposit, transaction, report, true));
                donationValue = transaction.getOutValue() - deposit.getAvailableAmmount();
                cIter.remove();
            }
        }
        reduceFee(transaction, report);
        //Print
        log.debug("Holding after donation: {}", getListOfHoldings(transaction.getOutCurrency()));
    }

    /*
     * Reine Gebühren Abzüge
     */
    public void reduceFee(Transaction transaction, Report report) {
        log.debug("REDUCE FEE");
        var feeValue = transaction.getFee();
        var usedDeposits = new ArrayList<Deposit>();
        if (transaction.getFee() != null) {
            log.debug("reduced At {}, with {} {} Fee",transaction.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME),transaction.getFeeCurrency().getTicker(), transaction.getFee());
            var currencyHold = getAvailableHoldings(transaction.getFeeCurrency());
            log.debug("Before Reduce: {}",getListOfHoldings(transaction.getFeeCurrency()));
            for (Iterator<Deposit> cIter = currencyHold.iterator(); cIter.hasNext();) {
                Deposit deposit = cIter.next();
                usedDeposits.add(deposit);
                Double dif = deposit.getAvailableAmmount() - transaction.getFee();
                log.debug("DIF= {}", dif);
                if ((-ZERO_LIMIT <= dif) && (dif <= ZERO_LIMIT)) {
                    cIter.remove();
                    break;
                }
                if (dif > ZERO_LIMIT) {
                    var withdraw = new Withdraw(feeValue,transaction.getOutCurrency(),transaction.getDateTime(), transaction.getExchange(),report, usedDeposits,true);
                    withdrawRepository.save(withdraw); //fee don't need extra table is a withdraw with isFee = true
                    deposit.setAvailableAmmount(dif);
                    depositRepository.save(deposit);
                    break;
                }
                if (dif < -ZERO_LIMIT) {
                    feeValue = transaction.getFee()- deposit.getAvailableAmmount();
                    cIter.remove();
                }
            }
            //Print
            log.debug("After Reduce: {}",getListOfHoldings(transaction.getFeeCurrency()));
        }
    }
    /*
     * service for holdings sortiert nach currency wie im alten design für die auswertung
     */

}
