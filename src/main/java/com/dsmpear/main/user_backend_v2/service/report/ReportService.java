package com.dsmpear.main.user_backend_v2.service.report;

import com.dsmpear.main.user_backend_v2.entity.report.enums.Field;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Grade;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Type;
import com.dsmpear.main.user_backend_v2.payload.request.report.BaseReportRequest;
import com.dsmpear.main.user_backend_v2.payload.request.report.SoleReportRequest;
import com.dsmpear.main.user_backend_v2.payload.request.report.TeamReportRequest;
import com.dsmpear.main.user_backend_v2.payload.response.ReportContentResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ReportListResponse;
import org.springframework.data.domain.Pageable;


public interface ReportService {

    ReportContentResponse getReport(Long reportId);

    ReportListResponse getReportList(Pageable pageable, Type type, Field field, Grade grade);

    Long deleteReport(Long reportId);
}
