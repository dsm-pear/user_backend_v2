package com.dsmpear.main.user_backend_v2.service.savereport;

import com.dsmpear.main.user_backend_v2.payload.request.report.SoleReportRequest;
import com.dsmpear.main.user_backend_v2.payload.request.report.TeamReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class SaveReportServiceImpl implements SaveReportService{

    private final SaveReportSupporter saveReportSupporter;

    @Override
    @Transactional
    public Long saveSoleReport(SoleReportRequest request) {
        return saveReportSupporter.saveReport(request).getId();
    }

    @Override
    @Transactional
    public Long updateSoleReport(SoleReportRequest request, Long reportId) {
        return saveReportSupporter.updateReportContent(request, reportId);
    }

    @Override
    @Transactional
    public Long saveTeamReport(TeamReportRequest request) {
        return saveReportSupporter.saveReport(request).getId();
    }

    @Override
    @Transactional
    public Long updateTeamReport(TeamReportRequest request, Long reportId) {
        return saveReportSupporter.updateReportContent(request, reportId);
    }

}
