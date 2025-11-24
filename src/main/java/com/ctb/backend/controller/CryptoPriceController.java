package com.ctb.backend.controller;

import com.ctb.backend.dto.CryptoPriceRequest;
import com.ctb.backend.model.CryptoPrice;
import com.ctb.backend.service.CryptoPriceService;
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
@RequestMapping("/api/prices")
@RequiredArgsConstructor
@Slf4j
public class CryptoPriceController {

    private final CryptoPriceService cryptoPriceService;

    @PostMapping
    public ResponseEntity<CryptoPrice> recordPrice(@Valid @RequestBody CryptoPriceRequest request) {
        log.info("POST /api/prices - Recording price for {}", request.getSymbol());
        CryptoPrice price = cryptoPriceService.recordPrice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(price);
    }

    @GetMapping
    public ResponseEntity<List<CryptoPrice>> getAllPrices(
            @RequestParam(required = false) String symbol,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        log.info("GET /api/prices - symbol: {}, startDate: {}, endDate: {}", symbol, startDate, endDate);
        
        List<CryptoPrice> prices;
        
        if (symbol != null && startDate != null && endDate != null) {
            prices = cryptoPriceService.getPricesBySymbolAndDateRange(symbol, startDate, endDate);
        } else if (symbol != null) {
            prices = cryptoPriceService.getPricesBySymbol(symbol);
        } else {
            prices = cryptoPriceService.getAllPrices();
        }
        
        return ResponseEntity.ok(prices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CryptoPrice> getPriceById(@PathVariable Long id) {
        log.info("GET /api/prices/{}", id);
        CryptoPrice price = cryptoPriceService.getPriceById(id);
        return ResponseEntity.ok(price);
    }

    @GetMapping("/latest/{symbol}")
    public ResponseEntity<CryptoPrice> getLatestPrice(@PathVariable String symbol) {
        log.info("GET /api/prices/latest/{}", symbol);
        CryptoPrice price = cryptoPriceService.getLatestPrice(symbol);
        return ResponseEntity.ok(price);
    }

    @GetMapping("/at-date")
    public ResponseEntity<CryptoPrice> getPriceAtDate(
            @RequestParam String symbol,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        log.info("GET /api/prices/at-date - symbol: {}, date: {}", symbol, date);
        CryptoPrice price = cryptoPriceService.getPriceAtDate(symbol, date);
        return ResponseEntity.ok(price);
    }
}
