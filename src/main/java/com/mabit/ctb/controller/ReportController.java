package com.mabit.ctb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mabit.ctb.entity.report.Report;
import com.mabit.ctb.exception.ReportException;
import com.mabit.ctb.service.ExportReportService;
import com.mabit.ctb.service.ReportService;


@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ExportReportService exportService;

    @GetMapping("/report/availableYears")
    public Iterable<Integer> getAvailableReportYears(){
        return reportService.availableReportYear();
    }

    @GetMapping("/report/reports")
    public Iterable<Report> getAvailableReports(){
        return reportService.availableReports();
    }

    @PostMapping("/report/updateReports")
    public ResponseEntity<Map<String, String>> createReport() throws ReportException{
            reportService.createReportEntries();
            return ResponseEntity.ok(createJsonResponse("Report Created"));
    }

    @GetMapping("/report/incomeReport/{reportId}")
    public ResponseEntity<byte[]> incomeReport(@PathVariable Long reportId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("income", "income.csv");
        var fileContent = exportService.getIncomeReport(reportId);

    return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

    @GetMapping("/report/donationReport/{reportId}")
    public ResponseEntity<byte[]> donationReport(@PathVariable Long reportId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("donation", "donation.csv");
        var fileContent = exportService.getDonationReport(reportId);

    return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

    @GetMapping("/report/gainReport/{reportId}")
    public ResponseEntity<byte[]> gainReport(@PathVariable Long reportId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("gain", "gain.csv");
        var fileContent = exportService.getGainReport(reportId);

    return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
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