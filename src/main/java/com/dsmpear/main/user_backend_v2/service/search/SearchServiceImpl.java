package com.dsmpear.main.user_backend_v2.service.search;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.mapper.ReportMapper;
import com.dsmpear.main.user_backend_v2.payload.response.ReportListResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    @Override
    public ReportListResponse searchReport(Pageable pageable, String title) {
        Page<Report> reportPage = reportRepository.findAllByReportTypeAccessAndIsAcceptedTrueAndIsSubmittedTrueAndTitleContainsOrderByCreatedAtDesc(Access.EVERY, title, pageable);

        return ReportListResponse.builder()
                .totalElements(reportPage.getTotalElements())
                .totalPages(reportPage.getTotalPages())
                .reportResponses(reportPage
                        .map(report -> reportMapper.entityToResponse(report))
                        .stream().collect(Collectors.toList()))
                .build();
    }
}
