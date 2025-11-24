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
@Table(name = "trades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Cryptocurrency symbol is required")
    @Column(nullable = false)
    private String cryptoSymbol;

    @NotNull(message = "Trade type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeType tradeType;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    @Column(nullable = false, precision = 20, scale = 8)
    private BigDecimal amount;

    @NotNull(message = "Price per unit is required")
    @Positive(message = "Price per unit must be positive")
    @Column(nullable = false, precision = 20, scale = 8)
    private BigDecimal pricePerUnit;

    @NotNull(message = "Trade date is required")
    @Column(nullable = false)
    private LocalDateTime tradeDate;

    @Column(precision = 20, scale = 8)
    private BigDecimal totalValue;

    @Column(length = 500)
    private String notes;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        calculateTotalValue();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        calculateTotalValue();
    }

    private void calculateTotalValue() {
        if (amount != null && pricePerUnit != null) {
            totalValue = amount.multiply(pricePerUnit);
        }
    }

    public enum TradeType {
        BUY,
        SELL
    }
}
