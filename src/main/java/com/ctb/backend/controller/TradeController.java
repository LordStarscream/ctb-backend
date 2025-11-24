package com.ctb.backend.controller;

import com.ctb.backend.dto.TradeRequest;
import com.ctb.backend.model.Trade;
import com.ctb.backend.service.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
@Slf4j
public class TradeController {

    private final TradeService tradeService;

    @PostMapping
    public ResponseEntity<Trade> createTrade(@Valid @RequestBody TradeRequest request) {
        log.info("POST /api/trades - Creating trade for {}", request.getCryptoSymbol());
        Trade trade = tradeService.createTrade(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(trade);
    }

    @GetMapping
    public ResponseEntity<List<Trade>> getAllTrades(
            @RequestParam(required = false) String symbol,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        log.info("GET /api/trades - symbol: {}, startDate: {}, endDate: {}", symbol, startDate, endDate);
        
        List<Trade> trades;
        
        if (symbol != null) {
            trades = tradeService.getTradesBySymbol(symbol);
        } else if (startDate != null && endDate != null) {
            trades = tradeService.getTradesByDateRange(startDate, endDate);
        } else {
            trades = tradeService.getAllTrades();
        }
        
        return ResponseEntity.ok(trades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trade> getTradeById(@PathVariable Long id) {
        log.info("GET /api/trades/{}", id);
        Trade trade = tradeService.getTradeById(id);
        return ResponseEntity.ok(trade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trade> updateTrade(
            @PathVariable Long id,
            @Valid @RequestBody TradeRequest request) {
        log.info("PUT /api/trades/{}", id);
        Trade trade = tradeService.updateTrade(id, request);
        return ResponseEntity.ok(trade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrade(@PathVariable Long id) {
        log.info("DELETE /api/trades/{}", id);
        tradeService.deleteTrade(id);
        return ResponseEntity.noContent().build();
    }
}
