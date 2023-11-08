package com.mabit.ctb.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabit.ctb.entity.Account;
import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.Transaction;
import com.mabit.ctb.entity.report.Income;
import com.mabit.ctb.entity.report.Report;
import com.mabit.ctb.exception.ReportException;
import com.mabit.ctb.repository.ReportRepository;
import com.mabit.ctb.repository.TransactionRepository;
import com.mabit.ctb.types.TransactionType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReportService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    HoldService holdingService;

    private static HashMap<Integer, Report> usedReports = new HashMap<>();

    private static final Double zeroLimit = 0.0000000000003;

    private Account account = null;

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

    public void createReportEntries() throws ReportException {
        Currency fiatCurrency = accountService.getBaseFiatCurrency();
        List<Transaction> transactions = transactionRepository.findByOrderByDateTimeAsc();
        //List<Gain> gains = new ArrayList<>(); // was report
        //List<Donation> donations = new ArrayList<>();
        List<Income> incomes = new ArrayList<>();
        this.account = accountService.getAccount();

        for (Transaction transaction : transactions) {
            boolean hasOut = (transaction.getOutCurrency() != null);
            boolean hasIn = (transaction.getInCurrency() != null);
            String inCurrency = (hasIn) ? transaction.getInCurrency().getTicker() + "=" + transaction.getInValue() : "empty";
            String outCurrency = (hasOut) ? transaction.getOutCurrency().getTicker() + "=" + transaction.getOutValue() : "empty";
            String fee = (transaction.getFee() != null) ? transaction.getFee() + " " + transaction.getFeeCurrency().getTicker() : "";
            Report report = getReport(transaction);
            log.debug("*** {} *** at: {}", transaction.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME), transaction.getExchange().getName());
            log.debug("inCurrency= " + inCurrency + ", outCurrency= " + outCurrency + ", Fee= " + fee);
            if (hasOut) {
                if (transaction.getOutCurrency().equals(fiatCurrency)) {
                    log.trace("Einzahlung in FiatCurrency (Euro)");
                    if (hasIn) {
                        log.trace("Einkauf {am} {cur} für FiatCurrency ",transaction.getInValue(),transaction.getInCurrency().getTicker());
                        holdingService.addToHoldings(transaction);
                    }
                } else { // könnte alles zusammen, interessiert nicht ob in euro oder nicht hauptsache hinzugefügt, wenn anders entfernt dann im hasIn abschnitt abhandeln
                    log.trace("Kauf/Trade in {cur}", transaction.getOutCurrency().getTicker());
                    if ((hasIn) && !(transaction.getInCurrency().equals(fiatCurrency))) //Auszahlungen in Euro .. Euro muss nicht in die Liste
                    {
                        log.trace("Einkauf {am} {cur} für {outam} {outcur} ",transaction.getInValue(),transaction.getInCurrency().
                            getTicker(),transaction.getOutValue(),transaction.getOutCurrency().getTicker());
                        holdingService.addToHoldings(transaction);
                    }
                }
                if (!hasIn) {
                    log.trace("Abgezogen von Verschiebung / Verschenkt");
                    log.trace("Before reduce hold ammount= " + holdingService.getAvailableHoldings(transaction.getOutCurrency()).get(0).getAvailableAmmount());
                    if (transaction.getType() == TransactionType.Donation) {
                        holdingService.addDonation(transaction, report);
                    }
                    /* !!! OTHER PROBLEMS kann das weg da bei auszahlung ja alles runter muss bei der einzahlung ist dann einfach die gebür automatisch schon weg? */
                    if (transaction.getType() == TransactionType.Withdraw) {
                        holdingService.reduceFee(transaction, report);
                    }
                    log.trace("After reduce  hold ammount= "+ holdingService.getAvailableHoldings(transaction.getOutCurrency()).get(0).getAvailableAmmount());
                    //muss aus holding liste gelöscht werden.. Geschenkliste ?
                }
            }

            if (hasIn) {
                if (transaction.getInCurrency().equals(fiatCurrency)) {
                    log.trace("Auszahlung in Euro");
                    if (hasOut) {
                        holdingService.addGain(transaction, report);
                    }
                } else {
                    log.trace("Auszahlung von " + transaction.getInCurrency().getTicker());
                    if ((hasOut) && !(transaction.getOutCurrency().equals(fiatCurrency))) //Einzahlung in Euro .. Euro muss nicht aus der Liste geholt werden
                    {
                        holdingService.addGain(transaction, report);
                    }
                }
                if (!hasOut) {
                    log.trace("Hinzugefügt von Verschiebung / Geschenkt / Dividente");
                    if (transaction.getType() == TransactionType.Gift || transaction.getType() == TransactionType.Income) {
                        holdingService.addIncome(transaction, report);
                    }
                    if (transaction.getType() == TransactionType.Deposit) {
                        holdingService.reduceFee(transaction, report);
                    }
                }
            }
        }
        //var reportContainer = new ReportContainer(gains, incomes, donations ,hold);
    }

    private Report getReport(Integer year){
        if (usedReports.containsKey(year))
            return usedReports.get(year);
        List<Report> reports = reportRepository.findByYear(year);
        if (!reports.isEmpty()){
            var report = reports.getFirst();
            usedReports.put(year, report);
            return reports.getFirst();
        }
        Report addReport = new Report(year, account);
        addReport = reportRepository.save(addReport);
        return addReport;
    }

    private Report getReport(Transaction transaction){
        return getReport(transaction.getDateTime().getYear());
    }
}
