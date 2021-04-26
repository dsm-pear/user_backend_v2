package com.dsmpear.main.user_backend_v2.factory;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.exception.ReportNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportFactory {

    private final ReportRepository reportRepository;

    public Report create(Long value) {
        return reportRepository.findById(value)
                .orElseThrow(ReportNotFoundException::new);
    }
}
