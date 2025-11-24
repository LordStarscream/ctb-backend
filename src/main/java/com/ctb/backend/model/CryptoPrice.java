package com.ctb.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "crypto_prices", 
       indexes = {
           @Index(name = "idx_symbol_date", columnList = "symbol,priceDate")
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Cryptocurrency symbol is required")
    @Column(nullable = false)
    private String symbol;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Column(nullable = false, precision = 20, scale = 8)
    private BigDecimal price;

    @NotNull(message = "Price date is required")
    @Column(nullable = false)
    private LocalDateTime priceDate;

    @Column(length = 50)
    private String source;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
