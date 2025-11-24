package com.ctb.backend.service;

import com.ctb.backend.dto.TradeRequest;
import com.ctb.backend.model.Trade;
import com.ctb.backend.repository.TradeRepository;
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
class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    private TradeRequest tradeRequest;
    private Trade trade;

    @BeforeEach
    void setUp() {
        tradeRequest = new TradeRequest(
            "BTC",
            Trade.TradeType.BUY,
            new BigDecimal("0.5"),
            new BigDecimal("50000"),
            LocalDateTime.of(2024, 1, 15, 10, 30),
            "Test trade"
        );

        trade = new Trade();
        trade.setId(1L);
        trade.setCryptoSymbol("BTC");
        trade.setTradeType(Trade.TradeType.BUY);
        trade.setAmount(new BigDecimal("0.5"));
        trade.setPricePerUnit(new BigDecimal("50000"));
        trade.setTradeDate(LocalDateTime.of(2024, 1, 15, 10, 30));
        trade.setNotes("Test trade");
    }

    @Test
    void createTrade_ShouldCreateTradeSuccessfully() {
        // Arrange
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        // Act
        Trade result = tradeService.createTrade(tradeRequest);

        // Assert
        assertNotNull(result);
        assertEquals("BTC", result.getCryptoSymbol());
        assertEquals(Trade.TradeType.BUY, result.getTradeType());
        verify(tradeRepository, times(1)).save(any(Trade.class));
    }

    @Test
    void getTradeById_ShouldReturnTrade() {
        // Arrange
        when(tradeRepository.findById(1L)).thenReturn(Optional.of(trade));

        // Act
        Trade result = tradeService.getTradeById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("BTC", result.getCryptoSymbol());
    }

    @Test
    void getTradeById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(tradeRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> tradeService.getTradeById(999L));
    }

    @Test
    void getAllTrades_ShouldReturnAllTrades() {
        // Arrange
        List<Trade> trades = Arrays.asList(trade);
        when(tradeRepository.findAllOrderByTradeDateDesc()).thenReturn(trades);

        // Act
        List<Trade> result = tradeService.getAllTrades();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("BTC", result.get(0).getCryptoSymbol());
    }

    @Test
    void getTradesBySymbol_ShouldReturnFilteredTrades() {
        // Arrange
        List<Trade> trades = Arrays.asList(trade);
        when(tradeRepository.findByCryptoSymbol("BTC")).thenReturn(trades);

        // Act
        List<Trade> result = tradeService.getTradesBySymbol("btc");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("BTC", result.get(0).getCryptoSymbol());
    }

    @Test
    void updateTrade_ShouldUpdateExistingTrade() {
        // Arrange
        when(tradeRepository.findById(1L)).thenReturn(Optional.of(trade));
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        TradeRequest updateRequest = new TradeRequest(
            "ETH",
            Trade.TradeType.SELL,
            new BigDecimal("1.0"),
            new BigDecimal("3000"),
            LocalDateTime.now(),
            "Updated trade"
        );

        // Act
        Trade result = tradeService.updateTrade(1L, updateRequest);

        // Assert
        assertNotNull(result);
        verify(tradeRepository, times(1)).save(any(Trade.class));
    }

    @Test
    void deleteTrade_ShouldDeleteTrade() {
        // Arrange
        when(tradeRepository.findById(1L)).thenReturn(Optional.of(trade));
        doNothing().when(tradeRepository).delete(trade);

        // Act
        tradeService.deleteTrade(1L);

        // Assert
        verify(tradeRepository, times(1)).delete(trade);
    }
}
