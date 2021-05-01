package com.dsmpear.main.user_backend_v2.facade.report;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.exception.ReportNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReportFacadeImpl implements ReportFacade {

    private final ReportRepository reportRepository;

    public Report createReport(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(ReportNotFoundException::new);
    }

}
