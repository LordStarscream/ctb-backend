package com.ctb.backend.controller;

import com.ctb.backend.dto.TaxReportResponse;
import com.ctb.backend.service.GermanTaxReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/tax-reports")
@RequiredArgsConstructor
@Slf4j
public class TaxReportController {

    private final GermanTaxReportService taxReportService;

    @GetMapping("/german/{year}")
    public ResponseEntity<TaxReportResponse> getGermanTaxReport(@PathVariable int year) {
        log.info("GET /api/tax-reports/german/{} - Generating German tax report", year);
        TaxReportResponse report = taxReportService.generateTaxReport(year);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/portfolio-value")
    public ResponseEntity<Map<String, BigDecimal>> getPortfolioValue(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        log.info("GET /api/tax-reports/portfolio-value - date: {}", date);
        Map<String, BigDecimal> portfolioValue = taxReportService.calculatePortfolioValue(date);
        return ResponseEntity.ok(portfolioValue);
    }
}
