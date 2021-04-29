package com.dsmpear.main.user_backend_v2.service.savereport;

import com.dsmpear.main.user_backend_v2.payload.request.report.SoleReportRequest;
import com.dsmpear.main.user_backend_v2.payload.request.report.TeamReportRequest;

public interface SaveReportService {
    Long saveSoleReport(SoleReportRequest request);

    Long tempSaveSoleReport(SoleReportRequest request, Long reportId);

    Long updateSoleReport(SoleReportRequest request, Long reportId);

    Long saveTeamReport(TeamReportRequest request);

    Long tempSaveTeamReport(TeamReportRequest request, Long reportId);

    Long updateTeamReport(TeamReportRequest request, Long reportId);
}
