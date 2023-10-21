package com.mabit.ctb.service;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.Transaction;
import com.mabit.ctb.entity.report.Donation;
import com.mabit.ctb.entity.report.Gain;
import com.mabit.ctb.entity.report.Hold;
import com.mabit.ctb.entity.report.Income;
import com.mabit.ctb.entity.report.ReportContainer;
import com.mabit.ctb.exception.ReportException;
import com.mabit.ctb.file.Representation;
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

    private static final Double zeroLimit = 0.0000000000003;

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

    public void generateReportEntry(){

    }

    private void createReport(Integer year) throws ReportException {
        Currency fiatCurrency = accountService.getBaseFiatCurrency();
        List<Transaction> transactions = transactionRepository.findByOrderByDateTimeAsc();
        HashMap<String, List<Hold>> hold = new HashMap<>();
        List<Gain> report = new ArrayList<>();
        List<Donation> donations = new ArrayList<>();
        List<Income> incomes = new ArrayList<>();

        for (Transaction w : transactions) {
            boolean hasOut = (w.getOutCurrency() != null);
            boolean hasIn = (w.getInCurrency() != null);
            String inCurrency = (hasIn) ? w.getInCurrency().getTicker() + "=" + w.getInValue() : "empty";
            String outCurrency = (hasOut) ? w.getOutCurrency().getTicker() + "=" + w.getOutValue() : "empty";
            String fee = (w.getFee() != null) ? w.getFee() + " " + w.getFeeCurrency().getTicker() : "";
            log.debug("*** {} *** at: {}", w.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME), w.getExchange().getName());
            log.debug("inCurrency= " + inCurrency + ", outCurrency= " + outCurrency + ", Fee= " + fee);
            if (hasOut) {
                if (w.getOutCurrency().equals(fiatCurrency)) {
                    log.trace("Einzahlung in Euro");
                    if (hasIn) {
                        addToHoldings(hold, w);
                    }
                } else { // könnte alles zusammen, interessiert nicht ob in euro oder nicht hauptsache hinzugefügt, wenn anders entfernt dann im hasIn abschnitt abhandeln
                    log.trace("Kauf/Trade in " + w.getOutCurrency().getTicker());
                    if ((hasIn) && !(w.getInCurrency().equals(fiatCurrency))) //Auszahlungen in Euro .. Euro muss nicht in die Liste
                    {
                        addToHoldings(hold, w);
                    }
                }
                if (!hasIn) {
                    log.trace("Abgezogen von Verschiebung / Verschenkt");
                    log.trace("Before reduce hold ammount= " + getHolding(hold, w.getOutCurrency()).get(0).getAmmount());
                    if (w.getType() == TransactionType.Donation) {
                        addDonation(donations, hold, w, year);
                    }
                    /* !!! OTHER PROBLEMS kann das weg da bei auszahlung ja alles runter muss bei der einzahlung ist dann einfach die gebür automatisch schon weg? */
                    if (w.getType() == TransactionType.Withdraw) {
                        reduceFee(hold, w);
                    }
                    log.trace("After reduce  hold ammount= "+getHolding(hold,w.getOutCurrency()).get(0).getAmmount());
                    //muss aus holding liste gelöscht werden.. Geschenkliste ?
                }
            }

            if (hasIn) {
                if (w.getInCurrency().equals(fiatCurrency)) {
                    log.trace("Auszahlung in Euro");
                    if (hasOut) {
                        addToReport(report, hold, w, year);
                    }
                } else {
                    log.trace("Auszahlung von " + w.getInCurrency().getTicker());
                    if ((hasOut) && !(w.getOutCurrency().equals(fiatCurrency))) //Einzahlung in Euro .. Euro muss nicht aus der Liste geholt werden
                    {
                        addToReport(report, hold, w, year);
                    }
                }
                if (!hasOut) {
                    log.trace("Hinzugefügt von Verschiebung / Geschenkt / Dividente");
                    if (w.getType() == TransactionType.Gift || w.getType() == TransactionType.Income) {
                        addIncome(incomes, hold, w, year);
                    }
                    if (w.getType() == TransactionType.Deposit) {
                        reduceFee(hold, w);
                    }
                }
            }
        }
        log.debug("Hold: {}", hold);
        var reportContainer = new ReportContainer(report, incomes, donations ,hold);
    }

    private List<Hold> getHolding(HashMap<String, List<Hold>> hold, Currency currency) {
        List<Hold> currencyHold;
        if (hold.containsKey(currency.getTicker())) {
            currencyHold = hold.get(currency.getTicker());
        } else {
            currencyHold = new ArrayList<Hold>();
            hold.put(currency.getTicker(), currencyHold);
        }
        return currencyHold;
    }

    private void addToHoldings(HashMap<String, List<Hold>> hold, Transaction wt) {
        log.debug("addToHoldings: {}",wt.getInCurrency().getTicker());
        List<Hold> currencyHold = getHolding(hold, wt.getInCurrency());
        //Print
        log.debug("Before Holding: {} ",getListOfHoldings(currencyHold, wt.getInCurrency()));
        //
        currencyHold.add(new Hold(wt.getInValue(), wt.getInCurrency(), wt.getDateTime(), wt.getExchange(), wt.getInFiatExchange().getFactor()));
        //Print
        log.debug("After  Holding: {} ",getListOfHoldings(currencyHold, wt.getInCurrency()));
        //
    }

    private String getListOfHoldings(List<Hold> list, Currency c){
        Double sum = 0.0;
        StringBuilder sb = new StringBuilder();
        sb.append(c.getTicker()).append(": ");
        for(Hold h:list){
            sum = sum+h.getAmmount();
            sb.append("[").append(h.getAmmount()).append("]");
        }
        sb.append(":: ").append(sum);
        return sb.toString();
    }

    private void reduceFee(HashMap<String, List<Hold>> hold, Transaction wt) {
        log.debug("REDUCE FEE");
        if (wt.getFee() != null) {
            log.debug("reduced At {}, with {} {} Fee",wt.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME),wt.getFeeCurrency().getTicker(), wt.getFee());
            List<Hold> currencyHold = getHolding(hold, wt.getFeeCurrency());
            log.debug("Before Reduce: {}",getListOfHoldings(currencyHold, wt.getFeeCurrency()));
            Double dif = 0.0;
            for (Iterator<Hold> cIter = currencyHold.iterator(); cIter.hasNext();) {
                Hold h = cIter.next();
                dif = h.getAmmount() - wt.getFee();
                log.debug("DIF= {}", dif);
                if ((-zeroLimit <= dif) && (dif <= zeroLimit)) {
                    cIter.remove();
                    break;
                }
                if (dif > zeroLimit) {
                    h.setAmmount(dif);
                    break;
                }
                if (dif < -zeroLimit) {
                    wt.setFee(wt.getFee()- h.getAmmount());
                    cIter.remove();
                }
            }
            //Print
            log.debug("After Reduce: {}",getListOfHoldings(currencyHold, wt.getFeeCurrency()));
        }
    }

    private Gain createGain(Hold hold, Transaction transaction){
        return createGain(hold,transaction,false);
    }

    private Gain createGain(Hold hold, Transaction transaction, boolean useHoldValue) {
        var value = useHoldValue?hold.getAmmount():transaction.getOutValue();
        boolean isShort = ChronoUnit.YEARS.between(hold.getDateTime(), transaction.getDateTime()) < 1;
        var short_long = "long";
        if (isShort)
                short_long = "short";
        var proceeds = value * transaction.getOutFiatExchange().getFactor(); //auf zwei stellen runden !!
        var costbasis = value * hold.getFactor();
        var profit = proceeds - costbasis;
        return new Gain(
                value,
                transaction.getOutCurrency(),
                hold.getDateTime(),
                transaction.getDateTime(),
                short_long,
                hold.getLocation(),
                transaction.getExchange(),
                proceeds,
                costbasis,
                profit);
    }

    private void addToReport(List<Gain> report, HashMap<String, List<Hold>> hold, Transaction wt, Integer year) throws ReportException {
        boolean partOfReport = (year == null || wt.getDateTime().getYear() == year);
        //if((year == null) || (true)) // between 01.01.year and 31.12.year
        List<Hold> currencyHold = getHolding(hold, wt.getOutCurrency());
        if (currencyHold.size() == 0) {
            throw new ReportException("No holding for Transaction");
        }
        Double dif = 0.0;
        var transactionDate = wt.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME);
        log.debug("addToReport of Transaction from {}",transactionDate);
        for (Iterator<Hold> cIter = currencyHold.iterator(); cIter.hasNext();) {
            //Print
            log.debug("Before Holding: {}", getListOfHoldings(currencyHold, wt.getOutCurrency()));
            //
            Hold h = cIter.next();
            if (wt.getOutCurrency().getTicker().equals("XRP")) //remove after solfing
                log.debug("++CHECK++");
            dif = h.getAmmount() - wt.getOutValue();
            if ((-zeroLimit <= dif) && (dif <= zeroLimit)) {
                log.debug("hold= " + h.getAmmount() + wt.getOutCurrency().getTicker() + " Transaction= " + wt.getOutValue() + " Fee= " + wt.getFee() + " in Currency =" + wt.getFeeCurrency());
                if (partOfReport)
                    report.add(createGain(h, wt));
                log.trace(" == 0");
                log.trace("Ammount h = " + h.getAmmount() + wt.getOutCurrency().getTicker());
                log.trace("Ammount wt = " + wt.getOutValue() + wt.getOutCurrency().getTicker() + " , Fee = " + wt.getFee());
                cIter.remove();
                log.trace("Element from holding removed");
                break;
            }
            if (dif > zeroLimit) {
                log.debug("hold=" + h.getAmmount() + wt.getOutCurrency().getTicker() + " Transaction= " + wt.getOutValue() + " Fee= " + wt.getFee() + " in Currency =" + wt.getFeeCurrency());
                if (partOfReport)
                    report.add(createGain(h, wt));
                log.trace(" > 0");
                log.trace("Ammount h = " + h.getAmmount() + wt.getOutCurrency().getTicker());
                log.trace("Ammount wt = " + wt.getOutValue() + wt.getOutCurrency().getTicker() + " , Fee = " + wt.getFee());
                h.setAmmount(dif); //eventuell mit 0 zusammen.. und wenn 0 dann remove
                //bleibt was übrig
                log.trace("Element remains with new Ammount = " + dif);
                dif = 0.0;
                break;
            }
            if (dif < -zeroLimit) {
                log.debug("hold=" + h.getAmmount() + wt.getOutCurrency().getTicker() + " Transaction= " + wt.getOutValue() + " Fee= " + wt.getFee() + " in Currency =" + wt.getFeeCurrency());
                if (partOfReport)
                    report.add(createGain(h, wt, true)); //currently all trades are handled seperatly, switch if like nowerdays in tradingView
                log.trace(" < 0");
                log.trace("Ammount h = " + h.getAmmount() + wt.getOutCurrency().getTicker());
                log.trace("Ammount wt = " + wt.getOutValue() + wt.getOutCurrency().getTicker() + " , Fee = " + wt.getFee());
                wt.setOutValue(wt.getOutValue() - h.getAmmount());
                cIter.remove();
                log.trace("rest = " + (wt.getOutValue() - h.getAmmount()) + wt.getOutCurrency());
                log.debug("Element from holding removed, next round to next holding, dif=" + dif);
            }
        }
        if ((dif > zeroLimit) || (dif < -zeroLimit)) {
            log.warn("For WalletTransaction {} no or not enaugh holding available: dif = {}",wt,dif);
            throw new ReportException("For WalletTransaction " + wt + " no or not enaugh holding available");
            // was wenn kein eintrag in h mehr vorhanden.. dann ist fehlt eine Einzahlung zur Auszahlung
        }
        log.debug("After  Holding: {}", getListOfHoldings(currencyHold, wt.getOutCurrency()));
    }

    private Donation createDonation(Hold hold, Transaction transaction) {
        return createDonation(hold,transaction,false);
    }

    private Donation createDonation(Hold hold, Transaction transaction, boolean useHoldValue) {
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
                fiatValue);
    }

    private void addDonation(List<Donation> donations, HashMap<String, List<Hold>> hold,  Transaction wt, Integer year) {
        boolean partOfReport = (year == null || wt.getDateTime().getYear() == year);
        List<Hold> currencyHold = getHolding(hold, wt.getOutCurrency());
        Double dif = 0.0;
        log.debug("donate At {}, with {} {} Value",wt.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME),wt.getFeeCurrency().getTicker(), wt.getOutValue());
        //Print
        log.debug("Holding before donation: {}", getListOfHoldings(currencyHold, wt.getOutCurrency()));
        for (Iterator<Hold> cIter = currencyHold.iterator(); cIter.hasNext();) {
            Hold h = cIter.next();
            dif = h.getAmmount() - wt.getOutValue();
            log.debug("DIF=  {}", dif);
            if ((-zeroLimit <= dif) && (dif <= zeroLimit)) {
                if (partOfReport)
                    donations.add(createDonation(h, wt));
                cIter.remove();
                break;
            }
            if (dif > zeroLimit) {
                if (partOfReport)
                    donations.add(createDonation(h, wt));
                h.setAmmount(dif);
                break;
            }
            if (dif < -zeroLimit) {
                if (partOfReport)
                    donations.add(createDonation(h, wt, true));
                wt.setOutValue(wt.getOutValue() - h.getAmmount());
                cIter.remove();
            }
        }
        reduceFee(hold, wt);
        //Print
        log.debug("Holding after donation: {}", getListOfHoldings(currencyHold, wt.getOutCurrency()));
    }

    private Income createIncome(List<Hold> hold, Transaction transaction){
        var fiatValue = transaction.getInValue() * transaction.getInFiatExchange().getFactor();
        return new Income(
                transaction.getInValue(),
                transaction.getInCurrency(),
                transaction.getDateTime(),
                transaction.getExchange(),
                transaction.getType(),
                transaction.getComment(),
                fiatValue
        );
    }

    private void addIncome(List<Income> incomes, HashMap<String, List<Hold>> hold, Transaction wt, Integer year) {
        boolean partOfReport = (year == null || wt.getDateTime().getYear() == year);
        List<Hold> currencyHold = getHolding(hold, wt.getInCurrency());
        this.addToHoldings(hold, wt);

        if(partOfReport){
            incomes.add(createIncome(currencyHold, wt));
        }
    }
}
