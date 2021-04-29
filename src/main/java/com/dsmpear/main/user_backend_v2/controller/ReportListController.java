package com.dsmpear.main.user_backend_v2.controller;

import com.dsmpear.main.user_backend_v2.entity.report.enums.Field;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Grade;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Type;
import com.dsmpear.main.user_backend_v2.payload.response.ReportListResponse;
import com.dsmpear.main.user_backend_v2.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/report/filter")
@RestController
public class ReportListController {

    private final ReportService reportService;

    @GetMapping
    public ReportListResponse getReportList(@RequestParam(required = false) Field field,
                                            @RequestParam(required = false) Type type,
                                            @RequestParam Grade grade,
                                            Pageable page) {
        return reportService.getReportList(page,type,field,grade);
    }

}
