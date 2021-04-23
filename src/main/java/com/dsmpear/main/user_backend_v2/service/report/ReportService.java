package com.dsmpear.main.user_backend_v2.service.report;

import com.dsmpear.main.user_backend_v2.payload.request.ReportRequest;
import com.dsmpear.main.user_backend_v2.payload.response.ReportContentResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ReportListResponse;

public interface ReportService {
    Long createReport(ReportRequest request);

    ReportContentResponse getReport(Long reportId);

    void updateReport(ReportRequest request);

    Long deleteReport(Long reportId);
}
