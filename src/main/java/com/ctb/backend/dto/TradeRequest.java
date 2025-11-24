package com.ctb.backend.dto;

import com.ctb.backend.model.Trade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeRequest {

    @NotBlank(message = "Cryptocurrency symbol is required")
    private String cryptoSymbol;

    @NotNull(message = "Trade type is required")
    private Trade.TradeType tradeType;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Price per unit is required")
    @Positive(message = "Price per unit must be positive")
    private BigDecimal pricePerUnit;

    @NotNull(message = "Trade date is required")
    private LocalDateTime tradeDate;

    private String notes;
}
