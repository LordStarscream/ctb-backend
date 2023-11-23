package com.mabit.ctb.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabit.ctb.file.report.DonationReport;
import com.mabit.ctb.file.report.GainReport;
import com.mabit.ctb.file.report.IncomeReport;
import com.mabit.ctb.repository.DonationRepository;
import com.mabit.ctb.repository.GainRepository;
import com.mabit.ctb.repository.IncomeRepository;
import com.mabit.ctb.repository.ReportRepository;

@Service
public class ExportReportService {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private GainRepository gainRepository;

    public byte[] getIncomeReport(Long reportId) {
        var income = incomeRepository.findByReportId(reportId);
        IncomeReport fileIncomeReport = new IncomeReport(income);
        return fileIncomeReport.export();
    }

    public byte[] getDonationReport(Long reportId) {
        var donation = donationRepository.findByReportId(reportId);
        DonationReport fileIncomeReport = new DonationReport(donation);
        return fileIncomeReport.export();
    }

    public byte[] getGainReport(Long reportId) {
        //var report = reportRepository.findById(reportId);
        var gain = gainRepository.findByReportId(reportId);
        GainReport fileIncomeReport = new GainReport(gain);
        return fileIncomeReport.export();
    }
}
