package com.dsmpear.main.user_backend_v2.service.comment;

import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl {

    private final ReportRepository reportRepository;
}
