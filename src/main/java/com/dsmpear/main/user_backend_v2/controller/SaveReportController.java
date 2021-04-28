package com.dsmpear.main.user_backend_v2.controller;

import com.dsmpear.main.user_backend_v2.payload.request.report.SoleReportRequest;
import com.dsmpear.main.user_backend_v2.payload.request.report.TeamReportRequest;
import com.dsmpear.main.user_backend_v2.service.savereport.SaveReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class SaveReportController {

    private final SaveReportService saveReportService;

    @PatchMapping("/sole/{reportId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Long updateSoleReport(@PathVariable Long reportId, @RequestBody @Valid SoleReportRequest reportRequest) {
        return saveReportService.updateSoleReport(reportRequest, reportId);
    }

    @PatchMapping("/team/{reportId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Long updateTeamReport(@PathVariable Long reportId, @RequestBody @Valid TeamReportRequest reportRequest) {
        return saveReportService.updateTeamReport(reportRequest, reportId);
    }

    @PostMapping("/sole")
    @ResponseStatus(HttpStatus.CREATED)
    public Long writeSoleReport(@RequestBody @Valid SoleReportRequest reportRequest) {
        return saveReportService.saveSoleReport(reportRequest);
    }

    @PostMapping("/team")
    @ResponseStatus(HttpStatus.CREATED)
    public Long writeTeamReport(@RequestBody @Valid TeamReportRequest reportRequest) {
        return saveReportService.saveTeamReport(reportRequest);
    }

    @PostMapping("/sole/{reportId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Long temporarySoleStorage(@RequestBody @Valid SoleReportRequest reportRequest, @PathVariable Long reportId) {
        return saveReportService.tempSaveSoleReport(reportRequest, reportId);
    }

    @PostMapping("/team/{reportId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Long temporaryTeamStorage(@RequestBody @Valid TeamReportRequest reportRequest, @PathVariable Long reportId) {
        return saveReportService.tempSaveTeamReport(reportRequest, reportId);
    }
}
