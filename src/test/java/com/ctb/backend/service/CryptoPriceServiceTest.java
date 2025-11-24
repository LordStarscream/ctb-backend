package com.ctb.backend.service;

import com.ctb.backend.dto.CryptoPriceRequest;
import com.ctb.backend.model.CryptoPrice;
import com.ctb.backend.repository.CryptoPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CryptoPriceServiceTest {

    @Mock
    private CryptoPriceRepository cryptoPriceRepository;

    @InjectMocks
    private CryptoPriceService cryptoPriceService;

    private CryptoPriceRequest priceRequest;
    private CryptoPrice cryptoPrice;

    @BeforeEach
    void setUp() {
        priceRequest = new CryptoPriceRequest(
            "BTC",
            new BigDecimal("50000"),
            LocalDateTime.of(2024, 1, 15, 10, 0),
            "CoinGecko"
        );

        cryptoPrice = new CryptoPrice();
        cryptoPrice.setId(1L);
        cryptoPrice.setSymbol("BTC");
        cryptoPrice.setPrice(new BigDecimal("50000"));
        cryptoPrice.setPriceDate(LocalDateTime.of(2024, 1, 15, 10, 0));
        cryptoPrice.setSource("CoinGecko");
    }

    @Test
    void recordPrice_ShouldRecordPriceSuccessfully() {
        // Arrange
        when(cryptoPriceRepository.save(any(CryptoPrice.class))).thenReturn(cryptoPrice);

        // Act
        CryptoPrice result = cryptoPriceService.recordPrice(priceRequest);

        // Assert
        assertNotNull(result);
        assertEquals("BTC", result.getSymbol());
        assertEquals(new BigDecimal("50000"), result.getPrice());
        verify(cryptoPriceRepository, times(1)).save(any(CryptoPrice.class));
    }

    @Test
    void getPriceById_ShouldReturnPrice() {
        // Arrange
        when(cryptoPriceRepository.findById(1L)).thenReturn(Optional.of(cryptoPrice));

        // Act
        CryptoPrice result = cryptoPriceService.getPriceById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("BTC", result.getSymbol());
    }

    @Test
    void getPriceById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(cryptoPriceRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cryptoPriceService.getPriceById(999L));
    }

    @Test
    void getAllPrices_ShouldReturnAllPrices() {
        // Arrange
        List<CryptoPrice> prices = Arrays.asList(cryptoPrice);
        when(cryptoPriceRepository.findAll()).thenReturn(prices);

        // Act
        List<CryptoPrice> result = cryptoPriceService.getAllPrices();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("BTC", result.get(0).getSymbol());
    }

    @Test
    void getPricesBySymbol_ShouldReturnFilteredPrices() {
        // Arrange
        List<CryptoPrice> prices = Arrays.asList(cryptoPrice);
        when(cryptoPriceRepository.findBySymbol("BTC")).thenReturn(prices);

        // Act
        List<CryptoPrice> result = cryptoPriceService.getPricesBySymbol("btc");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("BTC", result.get(0).getSymbol());
    }

    @Test
    void getLatestPrice_ShouldReturnLatestPrice() {
        // Arrange
        when(cryptoPriceRepository.findTopBySymbolOrderByPriceDateDesc("BTC"))
            .thenReturn(Optional.of(cryptoPrice));

        // Act
        CryptoPrice result = cryptoPriceService.getLatestPrice("btc");

        // Assert
        assertNotNull(result);
        assertEquals("BTC", result.getSymbol());
    }

    @Test
    void getLatestPrice_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(cryptoPriceRepository.findTopBySymbolOrderByPriceDateDesc("UNKNOWN"))
            .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cryptoPriceService.getLatestPrice("UNKNOWN"));
    }

    @Test
    void getPriceAtDate_ShouldReturnClosestPrice() {
        // Arrange
        LocalDateTime date = LocalDateTime.of(2024, 1, 15, 12, 0);
        when(cryptoPriceRepository.findClosestPriceBeforeDate("BTC", date))
            .thenReturn(Optional.of(cryptoPrice));

        // Act
        CryptoPrice result = cryptoPriceService.getPriceAtDate("btc", date);

        // Assert
        assertNotNull(result);
        assertEquals("BTC", result.getSymbol());
    }
}
