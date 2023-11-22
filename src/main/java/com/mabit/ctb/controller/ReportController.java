package com.mabit.ctb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mabit.ctb.entity.report.Report;
import com.mabit.ctb.exception.ReportException;
import com.mabit.ctb.service.ReportService;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/report/availableYears")
    public Iterable<Integer> getAvailableReportYears(){
        return reportService.availableReportYear();
    }

    @GetMapping("/report/reports")
    public Iterable<Report> getAvailableReports(){
        return reportService.availableReports();
    }

    @PostMapping("/report/createReport")
    public ResponseEntity<Map<String, String>> createReport(@RequestParam Integer year) throws ReportException{
            reportService.createReportEntries();
            return ResponseEntity.ok(createJsonResponse("Report Created"));
    }

    @ExceptionHandler(ReportException.class)
    public ResponseEntity<Map<String, String>> handleReportException(ReportException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createJsonResponse("Internal Server Error: " + ex.getMessage()));
    }

    private Map<String,String> createJsonResponse(String message){
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return response;
    }
}