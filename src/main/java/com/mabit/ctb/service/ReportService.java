package com.mabit.ctb.service;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabit.ctb.entity.Account;
import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.Transaction;
import com.mabit.ctb.entity.report.Donation;
import com.mabit.ctb.entity.report.Gain;
import com.mabit.ctb.entity.report.Hold;
import com.mabit.ctb.entity.report.Income;
import com.mabit.ctb.entity.report.Report;
import com.mabit.ctb.entity.report.ReportContainer;
import com.mabit.ctb.exception.ReportException;
import com.mabit.ctb.file.Representation;
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
        HashMap<String, List<Hold>> hold = new HashMap<>();
        List<Gain> gains = new ArrayList<>(); // was report
        List<Donation> donations = new ArrayList<>();
        List<Income> incomes = new ArrayList<>();
        this.account = accountService.getAccount();

        for (Transaction transaction : transactions) {
            boolean hasOut = (transaction.getOutCurrency() != null);
            boolean hasIn = (transaction.getInCurrency() != null);
            String inCurrency = (hasIn) ? transaction.getInCurrency().getTicker() + "=" + transaction.getInValue() : "empty";
            String outCurrency = (hasOut) ? transaction.getOutCurrency().getTicker() + "=" + transaction.getOutValue() : "empty";
            String fee = (transaction.getFee() != null) ? transaction.getFee() + " " + transaction.getFeeCurrency().getTicker() : "";
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
                    log.trace("Before reduce hold ammount= " + getHolding(hold, transaction.getOutCurrency()).get(0).getAmmount());
                    if (transaction.getType() == TransactionType.Donation) {
                        addDonation(transaction);//, year);
                    }
                    /* !!! OTHER PROBLEMS kann das weg da bei auszahlung ja alles runter muss bei der einzahlung ist dann einfach die gebür automatisch schon weg? */
                    if (transaction.getType() == TransactionType.Withdraw) {
                        reduceFee(hold, transaction);
                    }
                    log.trace("After reduce  hold ammount= "+getHolding(hold,transaction.getOutCurrency()).get(0).getAmmount());
                    //muss aus holding liste gelöscht werden.. Geschenkliste ?
                }
            }

            if (hasIn) {
                if (transaction.getInCurrency().equals(fiatCurrency)) {
                    log.trace("Auszahlung in Euro");
                    if (hasOut) {
                        addToReport(gains, hold, transaction);//, year);
                    }
                } else {
                    log.trace("Auszahlung von " + transaction.getInCurrency().getTicker());
                    if ((hasOut) && !(transaction.getOutCurrency().equals(fiatCurrency))) //Einzahlung in Euro .. Euro muss nicht aus der Liste geholt werden
                    {
                        addToReport(gains, hold, transaction); //, year);
                    }
                }
                if (!hasOut) {
                    log.trace("Hinzugefügt von Verschiebung / Geschenkt / Dividente");
                    if (transaction.getType() == TransactionType.Gift || transaction.getType() == TransactionType.Income) {
                        addIncome(incomes, hold, transaction);//, year);
                    }
                    if (transaction.getType() == TransactionType.Deposit) {
                        reduceFee(hold, transaction);
                    }
                }
            }
        }
        log.debug("Hold: {}", hold);
        var reportContainer = new ReportContainer(gains, incomes, donations ,hold);
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

    /*
     * Aktuelle Holdings für eine Währung holen
     */
    private List<Hold> getHolding(HashMap<String, List<Hold>> holdings, Currency currency) {
        List<Hold> currencyHold;
        if (holdings.containsKey(currency.getTicker())) {
            currencyHold = holdings.get(currency.getTicker());
        } else {
            currencyHold = new ArrayList<>();
            holdings.put(currency.getTicker(), currencyHold);
        }
        return currencyHold;
    }

    /*
     * Hinzufügen neuer Hinzugefügter Werte zu einer Währung
     */
    private void addToHoldings(HashMap<String, List<Hold>> holdings, Transaction transaction) {
        log.debug("addToHoldings: {}",transaction.getInCurrency().getTicker());
        List<Hold> currencyHold = getHolding(holdings, transaction.getInCurrency());
        //Print
        log.debug("Before Holding: {} ",getListOfHoldings(currencyHold, transaction.getInCurrency()));
        //
        currencyHold.add(new Hold(transaction.getInValue(), transaction.getInCurrency(), transaction.getDateTime(),
            transaction.getExchange(), transaction.getInFiatExchange().getFactor(),getReport(transaction)));
        //Print
        log.debug("After  Holding: {} ",getListOfHoldings(currencyHold, transaction.getInCurrency()));
        //
    }

    /*
     * Listing des aktuellen Inhalts einer Währung
     */
    private String getListOfHoldings(List<Hold> list, Currency currency){
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
     * Reine Gebühren Abzüge
     */
    private void reduceFee(HashMap<String, List<Hold>> holdings, Transaction transaction) {
        log.debug("REDUCE FEE");
        if (transaction.getFee() != null) {
            log.debug("reduced At {}, with {} {} Fee",transaction.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME),transaction.getFeeCurrency().getTicker(), transaction.getFee());
            List<Hold> currencyHold = getHolding(holdings, transaction.getFeeCurrency());
            log.debug("Before Reduce: {}",getListOfHoldings(currencyHold, transaction.getFeeCurrency()));
            Double dif = 0.0;
            for (Iterator<Hold> cIter = currencyHold.iterator(); cIter.hasNext();) {
                Hold hold = cIter.next();
                dif = hold.getAmmount() - transaction.getFee();
                log.debug("DIF= {}", dif);
                if ((-zeroLimit <= dif) && (dif <= zeroLimit)) {
                    cIter.remove();
                    break;
                }
                if (dif > zeroLimit) {
                    hold.setAmmount(dif);
                    break;
                }
                if (dif < -zeroLimit) {
                    transaction.setFee(transaction.getFee()- hold.getAmmount());
                    cIter.remove();
                }
            }
            //Print
            log.debug("After Reduce: {}",getListOfHoldings(currencyHold, transaction.getFeeCurrency()));
        }
    }

    private Gain createGain(Hold hold, Transaction transaction){
        return createGain(hold,transaction,false);
    }

    private Gain createGain(Hold hold, Transaction transaction, boolean useHoldValue) {
        var value = useHoldValue ? hold.getAmmount() : transaction.getOutValue();
        boolean isShort = ChronoUnit.YEARS.between(hold.getDateTime(), transaction.getDateTime()) < 1;
        var shortLong = "long";
        if (isShort)
                shortLong = "short";
        var proceeds = value * transaction.getOutFiatExchange().getFactor(); //auf zwei stellen runden !!
        var costbasis = value * hold.getFactor();
        var profit = proceeds - costbasis;
        return new Gain(
                value,
                transaction.getOutCurrency(),
                hold.getDateTime(),
                transaction.getDateTime(),
                shortLong,
                hold.getLocation(),
                transaction.getExchange(),
                proceeds,
                costbasis,
                profit,
                getReport(transaction));
    }

    private void addToReport(List<Gain> gains, HashMap<String, List<Hold>> holdings, Transaction transaction) throws ReportException {
        //boolean partOfReport = (year == null || transaction.getDateTime().getYear() == year);
        //if((year == null) || (true)) // between 01.01.year and 31.12.year
        List<Hold> currencyHold = getHolding(holdings, transaction.getOutCurrency());
        if (currencyHold.isEmpty()) {
            throw new ReportException("No holding for Transaction");
        }
        Double dif = 0.0;
        var transactionDate = transaction.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME);
        log.debug("addToReport of Transaction from {}",transactionDate);
        for (Iterator<Hold> cIter = currencyHold.iterator(); cIter.hasNext();) {
            //Print
            log.debug("Before Holding: {}", getListOfHoldings(currencyHold, transaction.getOutCurrency()));
            //
            Hold h = cIter.next();
            dif = h.getAmmount() - transaction.getOutValue();
            if ((-zeroLimit <= dif) && (dif <= zeroLimit)) {
                log.debug("hold= " + h.getAmmount() + transaction.getOutCurrency().getTicker() + " Transaction= " + transaction.getOutValue() + " Fee= " + transaction.getFee() + " in Currency =" + transaction.getFeeCurrency());
                gains.add(createGain(h, transaction)); // all independent of year
                log.trace(" == 0");
                log.trace("Ammount h = " + h.getAmmount() + transaction.getOutCurrency().getTicker());
                log.trace("Ammount wt = " + transaction.getOutValue() + transaction.getOutCurrency().getTicker() + " , Fee = " + transaction.getFee());
                cIter.remove();
                log.trace("Element from holding removed");
                break;
            }
            if (dif > zeroLimit) {
                log.debug("hold=" + h.getAmmount() + transaction.getOutCurrency().getTicker() + " Transaction= " + transaction.getOutValue() + " Fee= " + transaction.getFee() + " in Currency =" + transaction.getFeeCurrency());
                gains.add(createGain(h, transaction)); // all independent of year
                log.trace(" > 0");
                log.trace("Ammount h = " + h.getAmmount() + transaction.getOutCurrency().getTicker());
                log.trace("Ammount wt = " + transaction.getOutValue() + transaction.getOutCurrency().getTicker() + " , Fee = " + transaction.getFee());
                h.setAmmount(dif); //eventuell mit 0 zusammen.. und wenn 0 dann remove
                //bleibt was übrig
                log.trace("Element remains with new Ammount = " + dif);
                dif = 0.0;
                break;
            }
            if (dif < -zeroLimit) {
                log.debug("hold=" + h.getAmmount() + transaction.getOutCurrency().getTicker() + " Transaction= " + transaction.getOutValue() + " Fee= " + transaction.getFee() + " in Currency =" + transaction.getFeeCurrency());
                gains.add(createGain(h, transaction, true)); //currently all trades are handled seperatly, switch if like nowerdays in tradingView // all independent of year
                log.trace(" < 0");
                log.trace("Ammount h = " + h.getAmmount() + transaction.getOutCurrency().getTicker());
                log.trace("Ammount wt = " + transaction.getOutValue() + transaction.getOutCurrency().getTicker() + " , Fee = " + transaction.getFee());
                transaction.setOutValue(transaction.getOutValue() - h.getAmmount());
                cIter.remove();
                log.trace("rest = " + (transaction.getOutValue() - h.getAmmount()) + transaction.getOutCurrency());
                log.debug("Element from holding removed, next round to next holding, dif=" + dif);
            }
        }
        if ((dif > zeroLimit) || (dif < -zeroLimit)) {
            log.warn("For WalletTransaction {} no or not enaugh holding available: dif = {}",transaction,dif);
            throw new ReportException("For WalletTransaction " + transaction + " no or not enaugh holding available");
            // was wenn kein eintrag in h mehr vorhanden.. dann ist fehlt eine Einzahlung zur Auszahlung
        }
        log.debug("After  Holding: {}", getListOfHoldings(currencyHold, transaction.getOutCurrency()));
    }

    private Donation createDonation(Hold hold, Transaction transaction, Report report) {
        return createDonation(hold,transaction, report, false);
    }

    private Donation createDonation(Hold hold, Transaction transaction, Report report, boolean useHoldValue) {
        var value = useHoldValue ? hold.getAmmount() : transaction.getOutValue();
        var costbasis = value * hold.getFactor();
        var costBaseText = value.toString()+" "+
                transaction.getOutCurrency().getTicker()+" @ "+Representation.getPriceFormat(hold.getFactor())+
                " EUR \nAm "+hold.getDateTime().format(DateTimeFormatter.ISO_DATE)+ " bei "+hold.getLocation().getName();
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
/// TODO PROCEED HERE
    private void addDonation(List<Donation> donations, Transaction transaction) {
        Report report = getReport(transaction);
        List<Hold> currencyHold = holdingService.getHoldings(transaction.getOutCurrency());

        log.debug("donate At {}, with {} {} Value",transaction.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME),transaction.getFeeCurrency().getTicker(), transaction.getOutValue());
        //Print
        log.debug("Holding before donation: {}", getListOfHoldings(currencyHold, transaction.getOutCurrency()));
        for (Iterator<Hold> cIter = currencyHold.iterator(); cIter.hasNext();) {
            Hold hold = cIter.next();
            Double dif = hold.getAmmount() - transaction.getOutValue();
            log.debug("DIF=  {}", dif);
            if ((-zeroLimit <= dif) && (dif <= zeroLimit)){
                donations.add(createDonation(hold, transaction, report));
                cIter.remove();
                break;
            }
            if (dif > zeroLimit) {
                donations.add(createDonation(hold, transaction, report));
                hold.setAmmount(dif);
                break;
            }
            if (dif < -zeroLimit) {
                donations.add(createDonation(hold, transaction, report, true));
                transaction.setOutValue(transaction.getOutValue() - hold.getAmmount());
                cIter.remove();
            }
        }
        reduceFee(holdings, transaction);
        //Print
        log.debug("Holding after donation: {}", getListOfHoldings(currencyHold, transaction.getOutCurrency()));
    }

    private Income createIncome(Transaction transaction){
        var fiatValue = transaction.getInValue() * transaction.getInFiatExchange().getFactor();
        return new Income(
                transaction.getInValue(),
                transaction.getInCurrency(),
                transaction.getDateTime(),
                transaction.getExchange(),
                transaction.getType(),
                transaction.getComment(),
                fiatValue,
                getReport(transaction)
        );
    }

    private void addIncome(List<Income> incomes, HashMap<String, List<Hold>> holdings, Transaction transaction) {
        this.addToHoldings(holdings, transaction);
        incomes.add(createIncome(transaction));
    }
}
