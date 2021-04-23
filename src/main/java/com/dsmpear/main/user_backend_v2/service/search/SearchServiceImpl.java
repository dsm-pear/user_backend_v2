package com.dsmpear.main.user_backend_v2.service.search;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.mapper.ReportMapper;
import com.dsmpear.main.user_backend_v2.payload.response.ReportListResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    @Override
    public ReportListResponse searchReport(Pageable pageable, String title) {
        List<ReportResponse> reportResponses = new ArrayList<>();
        Page<Report> reportPage = reportRepository.findAllByReportTypeAccessEveryAndIsAcceptedTrueAndIsSubmittedTrueAndTitleContainsOrderByCreatedAtDesc(title, pageable);

        reportPage
                .map(report -> reportMapper.entityToResponse(report))
                .map(reportResponse -> reportResponses.add(reportResponse))
                .map(response -> ReportListResponse.builder()
                        .reportResponses(reportResponses)
                        .totalPages(reportPage.getTotalPages())
                        .totalElements(reportPage.getTotalElements()));
    }
}
