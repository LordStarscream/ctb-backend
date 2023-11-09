package com.mabit.ctb.service;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.Transaction;
import com.mabit.ctb.entity.report.Deposit;
import com.mabit.ctb.entity.report.Donation;
import com.mabit.ctb.entity.report.Gain;
import com.mabit.ctb.entity.report.Income;
import com.mabit.ctb.entity.report.Report;
import com.mabit.ctb.entity.report.Withdraw;
import com.mabit.ctb.exception.ReportException;
import com.mabit.ctb.file.Representation;
import com.mabit.ctb.repository.DepositRepository;
import com.mabit.ctb.repository.DonationRepository;
import com.mabit.ctb.repository.GainRepository;
import com.mabit.ctb.repository.IncomeRepository;
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

    @Autowired
    private GainRepository gainRepository;

    @Autowired
    private IncomeRepository incomeRepository;

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

    private void updateDeposit(Deposit deposit, Double value){
        deposit.setAvailableAmmount(value);
        depositRepository.save(deposit);
    }

    private void closeDeposit(Deposit deposit){
        deposit.setHasAvailableAmmount(false);
        updateDeposit(deposit, 0.0);
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

    /**
     *
     * @param transaction
     * @param report
     */
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
                closeDeposit(deposit);
                break;
            }
            if (dif > ZERO_LIMIT) {
                var withdraw = new Withdraw(donationValue,transaction.getOutCurrency(),transaction.getDateTime(), transaction.getExchange(),report, usedDeposits);
                withdrawRepository.save(withdraw);
                donationRepository.save(createDonation(deposit, transaction, report));
                updateDeposit(deposit, dif);
                break;
            }
            if (dif < -ZERO_LIMIT) {
                donationRepository.save(createDonation(deposit, transaction, report, true));
                donationValue = donationValue - deposit.getAvailableAmmount();
                closeDeposit(deposit);
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
                Double dif = deposit.getAvailableAmmount() - feeValue;
                log.debug("DIF= {}", dif);
                if ((-ZERO_LIMIT <= dif) && (dif <= ZERO_LIMIT)) {
                    closeDeposit(deposit);
                    break;
                }
                if (dif > ZERO_LIMIT) {
                    var withdraw = new Withdraw(feeValue, transaction.getFeeCurrency(), transaction.getDateTime(), transaction.getExchange(), report, usedDeposits,true);
                    withdrawRepository.save(withdraw); //fee don't need extra table is a withdraw with isFee = true
                    updateDeposit(deposit, dif);
                    break;
                }
                if (dif < -ZERO_LIMIT) {
                    feeValue = feeValue - deposit.getAvailableAmmount();
                    closeDeposit(deposit);
                }
            }
            //Print
            log.debug("After Reduce: {}",getListOfHoldings(transaction.getFeeCurrency()));
        }
    }

    /*
     * Realisierte Gewinne
     */
    private Gain createGain(Deposit deposit, Transaction transaction, Report report){
        return createGain(deposit, transaction, report, false);
    }

    private Gain createGain(Deposit deposit, Transaction transaction, Report report, boolean useHoldValue) {
        var value = useHoldValue ? deposit.getAvailableAmmount() : transaction.getOutValue();
        boolean isShort = ChronoUnit.YEARS.between(deposit.getDateTime(), transaction.getDateTime()) < 1;
        var shortLong = "long";
        if (isShort)
                shortLong = "short";
        var proceeds = value * transaction.getOutFiatExchange().getFactor(); //auf zwei stellen runden !!
        var costbasis = value * deposit.getFactor();
        var profit = proceeds - costbasis;
        return new Gain(
                value,
                transaction.getOutCurrency(),
                deposit.getDateTime(),
                transaction.getDateTime(),
                shortLong,
                deposit.getLocation(),
                transaction.getExchange(),
                proceeds,
                costbasis,
                profit,
                report);
    }


    public void addGain(Transaction transaction, Report report) throws ReportException {
        //boolean partOfReport = (year == null || transaction.getDateTime().getYear() == year);
        //if((year == null) || (true)) // between 01.01.year and 31.12.year
        var gainValue = transaction.getOutValue();
        var usedDeposits = new ArrayList<Deposit>();
        var currencyHold = getAvailableHoldings(transaction.getOutCurrency());
        Double dif = 0.0;
        if (currencyHold.isEmpty()) {
            throw new ReportException("No holding for Transaction");
        }
        var transactionDate = transaction.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME);
        log.debug("addGain of Transaction from {}",transactionDate);
        for (Iterator<Deposit> cIter = currencyHold.iterator(); cIter.hasNext();) {
            //Print
            log.debug("Before Holding: {}", getListOfHoldings(transaction.getOutCurrency()));
            Deposit deposit = cIter.next();
            usedDeposits.add(deposit);
            dif = deposit.getAvailableAmmount() - gainValue;
            if ((-ZERO_LIMIT <= dif) && (dif <= ZERO_LIMIT)) {
                log.debug("hold= " + deposit.getAvailableAmmount() + transaction.getOutCurrency().getTicker() + " Substraction Value= " + gainValue + " Fee= " + transaction.getFee() + " in Currency =" + transaction.getFeeCurrency());
                var withdraw = new Withdraw(gainValue,transaction.getOutCurrency(),transaction.getDateTime(), transaction.getExchange(),report, usedDeposits);
                withdrawRepository.save(withdraw);
                gainRepository.save(createGain(deposit, transaction, report));
                closeDeposit(deposit);
                log.trace("Element from holding removed");
                break;
            }
            if (dif > ZERO_LIMIT) {
                log.debug("hold=" + deposit.getAvailableAmmount() + transaction.getOutCurrency().getTicker() + " Substraction Value= " + gainValue + " Fee= " + transaction.getFee() + " in Currency =" + transaction.getFeeCurrency());
                var withdraw = new Withdraw(gainValue,transaction.getOutCurrency(),transaction.getDateTime(), transaction.getExchange(),report, usedDeposits);
                withdrawRepository.save(withdraw);
                gainRepository.save(createGain(deposit, transaction, report));
                updateDeposit(deposit, dif);
                //bleibt was übrig
                log.trace("Element remains with new Ammount = " + dif);
                dif = 0.0;
                break;
            }
            if (dif < -ZERO_LIMIT) {
                log.debug("hold=" + deposit.getAvailableAmmount() + transaction.getOutCurrency().getTicker() + " Substraction Value= " + gainValue + " Fee= " + transaction.getFee() + " in Currency =" + transaction.getFeeCurrency());
                gainRepository.save(createGain(deposit, transaction, report, true)); //currently all trades are handled seperatly, switch if like nowerdays in tradingView // all independent of year
                log.trace(" < 0");
                log.trace("Ammount h = " + deposit.getAvailableAmmount() + transaction.getOutCurrency().getTicker());
                log.trace("Ammount wt = " + transaction.getOutValue() + transaction.getOutCurrency().getTicker() + " , Fee = " + transaction.getFee());
                gainValue = gainValue - deposit.getAvailableAmmount();
                log.trace("rest = " + (transaction.getOutValue() - deposit.getAvailableAmmount()) + transaction.getOutCurrency());
                log.debug("Element from holding removed, next round to next holding, dif=" + dif);
                closeDeposit(deposit);
            }
        }
        if ((dif > ZERO_LIMIT) || (dif < -ZERO_LIMIT)) {
            log.warn("For WalletTransaction {} no or not enaugh holding available: dif = {}",transaction,dif);
            throw new ReportException("For WalletTransaction " + transaction + " no or not enaugh holding available");
            // was wenn kein eintrag in h mehr vorhanden.. dann ist fehlt eine Einzahlung zur Auszahlung
        }
        log.debug("After  Holding: {}", getListOfHoldings(transaction.getOutCurrency()));
    }

    /*
     * Incomes Überweisungen / Erstattungen / Bonus usw.
     */

     private Income createIncome(Transaction transaction, Report report){
        var fiatValue = transaction.getInValue() * transaction.getInFiatExchange().getFactor();
        return new Income(
                transaction.getInValue(),
                transaction.getInCurrency(),
                transaction.getDateTime(),
                transaction.getExchange(),
                transaction.getType(),
                transaction.getComment(),
                fiatValue,
                report
        );
    }

    public void addIncome(Transaction transaction, Report report) {
        addToHoldings(transaction);
        incomeRepository.save(createIncome(transaction, report));
    }

    /*
     * service for holdings sortiert nach currency wie im alten design für die auswertung
     */

}
