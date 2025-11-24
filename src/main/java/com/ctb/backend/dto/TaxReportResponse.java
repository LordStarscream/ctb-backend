package com.ctb.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxReportResponse {

    private LocalDateTime reportDate;
    private Integer taxYear;
    private BigDecimal totalGains;
    private BigDecimal totalLosses;
    private BigDecimal netGainLoss;
    private List<TaxableEvent> taxableEvents;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TaxableEvent {
        private Long tradeId;
        private String cryptoSymbol;
        private LocalDateTime tradeDate;
        private BigDecimal amount;
        private BigDecimal buyPrice;
        private BigDecimal sellPrice;
        private BigDecimal gainLoss;
        private String description;
    }
}
