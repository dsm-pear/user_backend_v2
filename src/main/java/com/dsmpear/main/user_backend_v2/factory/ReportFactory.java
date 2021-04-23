package com.dsmpear.main.user_backend_v2.factory;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.exception.ReportNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportFactory implements Factory<ReportRepository, Report> {

    private final ReportRepository reportRepository;

    @Override
    public Report create(String value) {
        return reportRepository.findById(Integer.parseInt(value))
                .orElseThrow(ReportNotFoundException::new);
    }
}
