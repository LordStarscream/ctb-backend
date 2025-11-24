package com.ctb.backend.service;

import com.ctb.backend.dto.TaxReportResponse;
import com.ctb.backend.model.CryptoPrice;
import com.ctb.backend.model.Trade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for calculating German tax reports based on FIFO (First In First Out) method.
 * German tax law requires crypto gains to be taxed if held for less than 1 year.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GermanTaxReportService {

    private final TradeService tradeService;
    private final CryptoPriceService cryptoPriceService;

    /**
     * Generate tax report for a specific year using FIFO method
     */
    public TaxReportResponse generateTaxReport(int year) {
        log.info("Generating tax report for year: {}", year);
        
        LocalDateTime startDate = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(year, 12, 31, 23, 59);
        
        List<Trade> yearTrades = tradeService.getTradesByDateRange(startDate, endDate);
        
        if (yearTrades.isEmpty()) {
            log.info("No trades found for year: {}", year);
            return TaxReportResponse.builder()
                .reportDate(LocalDateTime.now())
                .taxYear(year)
                .totalGains(BigDecimal.ZERO)
                .totalLosses(BigDecimal.ZERO)
                .netGainLoss(BigDecimal.ZERO)
                .taxableEvents(new ArrayList<>())
                .build();
        }
        
        List<TaxReportResponse.TaxableEvent> taxableEvents = calculateTaxableEvents(yearTrades);
        
        BigDecimal totalGains = taxableEvents.stream()
            .map(TaxReportResponse.TaxableEvent::getGainLoss)
            .filter(gain -> gain.compareTo(BigDecimal.ZERO) > 0)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalLosses = taxableEvents.stream()
            .map(TaxReportResponse.TaxableEvent::getGainLoss)
            .filter(loss -> loss.compareTo(BigDecimal.ZERO) < 0)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal netGainLoss = totalGains.add(totalLosses);
        
        log.info("Tax report generated: Total gains: {}, Total losses: {}, Net: {}", 
            totalGains, totalLosses, netGainLoss);
        
        return TaxReportResponse.builder()
            .reportDate(LocalDateTime.now())
            .taxYear(year)
            .totalGains(totalGains)
            .totalLosses(totalLosses)
            .netGainLoss(netGainLoss)
            .taxableEvents(taxableEvents)
            .build();
    }

    /**
     * Calculate taxable events using FIFO method
     */
    private List<TaxReportResponse.TaxableEvent> calculateTaxableEvents(List<Trade> trades) {
        List<TaxReportResponse.TaxableEvent> taxableEvents = new ArrayList<>();
        
        // Group trades by crypto symbol
        Map<String, List<Trade>> tradesBySymbol = trades.stream()
            .collect(Collectors.groupingBy(Trade::getCryptoSymbol));
        
        // Process each symbol separately using FIFO
        for (Map.Entry<String, List<Trade>> entry : tradesBySymbol.entrySet()) {
            String symbol = entry.getKey();
            List<Trade> symbolTrades = entry.getValue();
            
            // Sort by trade date
            symbolTrades.sort(Comparator.comparing(Trade::getTradeDate));
            
            // FIFO queue for buy trades
            Queue<Trade> buyQueue = new LinkedList<>();
            
            for (Trade trade : symbolTrades) {
                if (trade.getTradeType() == Trade.TradeType.BUY) {
                    buyQueue.offer(trade);
                } else if (trade.getTradeType() == Trade.TradeType.SELL) {
                    // Process sell against oldest buys (FIFO)
                    BigDecimal remainingSellAmount = trade.getAmount();
                    
                    while (remainingSellAmount.compareTo(BigDecimal.ZERO) > 0 && !buyQueue.isEmpty()) {
                        Trade buyTrade = buyQueue.peek();
                        BigDecimal buyAmount = buyTrade.getAmount();
                        
                        BigDecimal matchedAmount = remainingSellAmount.min(buyAmount);
                        
                        // Calculate gain/loss
                        BigDecimal buyValue = matchedAmount.multiply(buyTrade.getPricePerUnit());
                        BigDecimal sellValue = matchedAmount.multiply(trade.getPricePerUnit());
                        BigDecimal gainLoss = sellValue.subtract(buyValue)
                            .setScale(2, RoundingMode.HALF_UP);
                        
                        TaxReportResponse.TaxableEvent event = TaxReportResponse.TaxableEvent.builder()
                            .tradeId(trade.getId())
                            .cryptoSymbol(symbol)
                            .tradeDate(trade.getTradeDate())
                            .amount(matchedAmount)
                            .buyPrice(buyTrade.getPricePerUnit())
                            .sellPrice(trade.getPricePerUnit())
                            .gainLoss(gainLoss)
                            .description(String.format("Sold %s %s (bought at %s, sold at %s)",
                                matchedAmount, symbol, buyTrade.getPricePerUnit(), trade.getPricePerUnit()))
                            .build();
                        
                        taxableEvents.add(event);
                        
                        // Update amounts
                        remainingSellAmount = remainingSellAmount.subtract(matchedAmount);
                        buyTrade.setAmount(buyAmount.subtract(matchedAmount));
                        
                        // Remove from queue if fully consumed
                        if (buyTrade.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                            buyQueue.poll();
                        }
                    }
                }
            }
        }
        
        return taxableEvents;
    }

    /**
     * Calculate portfolio value at a specific date
     */
    public Map<String, BigDecimal> calculatePortfolioValue(LocalDateTime date) {
        log.info("Calculating portfolio value at date: {}", date);
        
        List<Trade> allTrades = tradeService.getAllTrades();
        
        // Filter trades up to the specified date
        List<Trade> tradesUpToDate = allTrades.stream()
            .filter(trade -> !trade.getTradeDate().isAfter(date))
            .collect(Collectors.toList());
        
        // Calculate holdings per symbol
        Map<String, BigDecimal> holdings = new HashMap<>();
        
        for (Trade trade : tradesUpToDate) {
            String symbol = trade.getCryptoSymbol();
            BigDecimal amount = trade.getAmount();
            
            holdings.putIfAbsent(symbol, BigDecimal.ZERO);
            
            if (trade.getTradeType() == Trade.TradeType.BUY) {
                holdings.put(symbol, holdings.get(symbol).add(amount));
            } else {
                holdings.put(symbol, holdings.get(symbol).subtract(amount));
            }
        }
        
        // Calculate value for each holding
        Map<String, BigDecimal> portfolioValue = new HashMap<>();
        
        for (Map.Entry<String, BigDecimal> entry : holdings.entrySet()) {
            String symbol = entry.getKey();
            BigDecimal amount = entry.getValue();
            
            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                try {
                    CryptoPrice price = cryptoPriceService.getPriceAtDate(symbol, date);
                    BigDecimal value = amount.multiply(price.getPrice())
                        .setScale(2, RoundingMode.HALF_UP);
                    portfolioValue.put(symbol, value);
                } catch (RuntimeException e) {
                    log.warn("Could not find price for {} at {}: {}", symbol, date, e.getMessage());
                    portfolioValue.put(symbol, BigDecimal.ZERO);
                }
            }
        }
        
        log.info("Portfolio value calculated: {}", portfolioValue);
        return portfolioValue;
    }
}
