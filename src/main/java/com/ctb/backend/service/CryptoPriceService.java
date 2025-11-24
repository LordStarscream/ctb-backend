package com.ctb.backend.service;

import com.ctb.backend.dto.CryptoPriceRequest;
import com.ctb.backend.model.CryptoPrice;
import com.ctb.backend.repository.CryptoPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoPriceService {

    private final CryptoPriceRepository cryptoPriceRepository;

    @Transactional
    public CryptoPrice recordPrice(CryptoPriceRequest request) {
        log.info("Recording price for symbol: {} at {}", request.getSymbol(), request.getPriceDate());
        
        CryptoPrice cryptoPrice = new CryptoPrice();
        cryptoPrice.setSymbol(request.getSymbol().toUpperCase());
        cryptoPrice.setPrice(request.getPrice());
        cryptoPrice.setPriceDate(request.getPriceDate());
        cryptoPrice.setSource(request.getSource() != null ? request.getSource() : "MANUAL");
        
        CryptoPrice savedPrice = cryptoPriceRepository.save(cryptoPrice);
        log.info("Price recorded with ID: {}", savedPrice.getId());
        
        return savedPrice;
    }

    @Transactional(readOnly = true)
    public CryptoPrice getPriceById(Long id) {
        log.info("Fetching price with ID: {}", id);
        return cryptoPriceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Price not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<CryptoPrice> getAllPrices() {
        log.info("Fetching all prices");
        return cryptoPriceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CryptoPrice> getPricesBySymbol(String symbol) {
        log.info("Fetching prices for symbol: {}", symbol);
        return cryptoPriceRepository.findBySymbol(symbol.toUpperCase());
    }

    @Transactional(readOnly = true)
    public List<CryptoPrice> getPricesBySymbolAndDateRange(
            String symbol, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Fetching prices for symbol: {} between {} and {}", symbol, startDate, endDate);
        return cryptoPriceRepository.findBySymbolAndPriceDateBetween(
            symbol.toUpperCase(), startDate, endDate);
    }

    @Transactional(readOnly = true)
    public CryptoPrice getPriceAtDate(String symbol, LocalDateTime date) {
        log.info("Fetching price for symbol: {} at date: {}", symbol, date);
        return cryptoPriceRepository.findClosestPriceBeforeDate(symbol.toUpperCase(), date)
            .orElseThrow(() -> new RuntimeException(
                "No price found for symbol " + symbol + " before date " + date));
    }

    @Transactional(readOnly = true)
    public CryptoPrice getLatestPrice(String symbol) {
        log.info("Fetching latest price for symbol: {}", symbol);
        return cryptoPriceRepository.findTopBySymbolOrderByPriceDateDesc(symbol.toUpperCase())
            .orElseThrow(() -> new RuntimeException("No price found for symbol: " + symbol));
    }
}
