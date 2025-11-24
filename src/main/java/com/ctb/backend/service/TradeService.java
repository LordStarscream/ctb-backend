package com.ctb.backend.service;

import com.ctb.backend.dto.TradeRequest;
import com.ctb.backend.model.Trade;
import com.ctb.backend.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeService {

    private final TradeRepository tradeRepository;

    @Transactional
    public Trade createTrade(TradeRequest request) {
        log.info("Creating new trade for symbol: {}", request.getCryptoSymbol());
        
        Trade trade = new Trade();
        trade.setCryptoSymbol(request.getCryptoSymbol().toUpperCase());
        trade.setTradeType(request.getTradeType());
        trade.setAmount(request.getAmount());
        trade.setPricePerUnit(request.getPricePerUnit());
        trade.setTradeDate(request.getTradeDate());
        trade.setNotes(request.getNotes());
        
        Trade savedTrade = tradeRepository.save(trade);
        log.info("Trade created with ID: {}", savedTrade.getId());
        
        return savedTrade;
    }

    @Transactional(readOnly = true)
    public Trade getTradeById(Long id) {
        log.info("Fetching trade with ID: {}", id);
        return tradeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Trade not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Trade> getAllTrades() {
        log.info("Fetching all trades");
        return tradeRepository.findAllOrderByTradeDateDesc();
    }

    @Transactional(readOnly = true)
    public List<Trade> getTradesBySymbol(String symbol) {
        log.info("Fetching trades for symbol: {}", symbol);
        return tradeRepository.findByCryptoSymbol(symbol.toUpperCase());
    }

    @Transactional(readOnly = true)
    public List<Trade> getTradesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Fetching trades between {} and {}", startDate, endDate);
        return tradeRepository.findByTradeDateBetween(startDate, endDate);
    }

    @Transactional
    public Trade updateTrade(Long id, TradeRequest request) {
        log.info("Updating trade with ID: {}", id);
        
        Trade trade = getTradeById(id);
        trade.setCryptoSymbol(request.getCryptoSymbol().toUpperCase());
        trade.setTradeType(request.getTradeType());
        trade.setAmount(request.getAmount());
        trade.setPricePerUnit(request.getPricePerUnit());
        trade.setTradeDate(request.getTradeDate());
        trade.setNotes(request.getNotes());
        
        Trade updatedTrade = tradeRepository.save(trade);
        log.info("Trade updated: {}", updatedTrade.getId());
        
        return updatedTrade;
    }

    @Transactional
    public void deleteTrade(Long id) {
        log.info("Deleting trade with ID: {}", id);
        Trade trade = getTradeById(id);
        tradeRepository.delete(trade);
        log.info("Trade deleted: {}", id);
    }
}
