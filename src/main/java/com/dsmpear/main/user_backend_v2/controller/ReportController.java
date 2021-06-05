package com.dsmpear.main.user_backend_v2.controller;

import com.dsmpear.main.user_backend_v2.payload.response.ReportContentResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ReportModifyResponse;
import com.dsmpear.main.user_backend_v2.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/report")
@RestController
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/{reportId}")
    public ReportContentResponse getReportContent(@PathVariable Long reportId) {
        return reportService.getReport(reportId);
    }

    @GetMapping("/modify/{reportId}")
    public ReportModifyResponse getReportModify(@PathVariable Long reportId) {
        return reportService.getReportModify(reportId);
    }

    @DeleteMapping("/{reportId}")
    public Long deleteReport(@PathVariable Long reportId) {
        return reportService.deleteReport(reportId);
    }

}