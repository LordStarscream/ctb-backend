package com.ctb.backend.repository;

import com.ctb.backend.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    List<Trade> findByCryptoSymbol(String cryptoSymbol);

    List<Trade> findByTradeType(Trade.TradeType tradeType);

    List<Trade> findByTradeDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT t FROM Trade t WHERE t.cryptoSymbol = :symbol AND t.tradeDate BETWEEN :startDate AND :endDate")
    List<Trade> findBySymbolAndDateRange(
        @Param("symbol") String symbol,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT t FROM Trade t ORDER BY t.tradeDate DESC")
    List<Trade> findAllOrderByTradeDateDesc();
}
