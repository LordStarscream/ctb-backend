package com.ctb.backend.repository;

import com.ctb.backend.model.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, Long> {

    List<CryptoPrice> findBySymbol(String symbol);

    List<CryptoPrice> findBySymbolAndPriceDateBetween(
        String symbol,
        LocalDateTime startDate,
        LocalDateTime endDate
    );

    @Query("SELECT cp FROM CryptoPrice cp WHERE cp.symbol = :symbol AND cp.priceDate <= :date ORDER BY cp.priceDate DESC LIMIT 1")
    Optional<CryptoPrice> findClosestPriceBeforeDate(
        @Param("symbol") String symbol,
        @Param("date") LocalDateTime date
    );

    Optional<CryptoPrice> findTopBySymbolOrderByPriceDateDesc(String symbol);
}
