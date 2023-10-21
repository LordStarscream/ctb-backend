package com.mabit.ctb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mabit.ctb.service.ReportService;

@RestController
public class ReportController {
   @Autowired
    private ReportService reportService;

    @GetMapping("/report/availableYears")
    public Iterable<Integer> getAvailableReportYears(){
        return reportService.availableReportYear();
    }

    @PostMapping("/report/createReport")
    public ResponseEntity<Map<String, String>> createReport(@RequestParam Integer year){
        return ResponseEntity.ok(createJsonResponse("Report Created"));
    }

    private Map<String,String> createJsonResponse(String message){
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return response;
    }
}