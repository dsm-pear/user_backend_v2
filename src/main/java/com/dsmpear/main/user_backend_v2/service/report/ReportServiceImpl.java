package com.dsmpear.main.user_backend_v2.service.report;

import com.dsmpear.main.user_backend_v2.entity.comment.Comment;
import com.dsmpear.main.user_backend_v2.entity.comment.CommentRepository;
import com.dsmpear.main.user_backend_v2.entity.language.Language;
import com.dsmpear.main.user_backend_v2.entity.notice.Notice;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.exception.InvalidAccessException;
import com.dsmpear.main.user_backend_v2.exception.ReportNotFoundException;
import com.dsmpear.main.user_backend_v2.factory.UserFactory;
import com.dsmpear.main.user_backend_v2.mapper.LanguageMapper;
import com.dsmpear.main.user_backend_v2.mapper.ReportMapper;
import com.dsmpear.main.user_backend_v2.mapper.ReportTypeMapper;
import com.dsmpear.main.user_backend_v2.payload.request.ReportRequest;
import com.dsmpear.main.user_backend_v2.payload.response.ReportContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final CommentRepository commentRepository;
    private final UserFactory userFactory;

    private final ReportMapper reportMapper;
    private final ReportTypeMapper reportTypeMapper;
    private final LanguageMapper languageMapper;

    @Override
    public Long createReport(ReportRequest request) {
        Report report = reportMapper.requestToEntity(request, userFactory.createAuthUser());

        request.getLanguages().stream()
                .map(language -> languageMapper.requestToEntity(language, report))
                .collect(Collectors.toList());

        reportTypeMapper.requestToEntity(request, report);

        return reportRepository.save(report).getId();
    }

    @Override
    public ReportContentResponse getReport(Long reportId) {
        return null;
//        Report report = reportRepository.findById(reportId)
//                .orElseThrow(ReportNotFoundException::new);
//        List<Comment> comments = commentRepository.findAllByReport(report);
//
//        if(isAccessable(report)) {
//            throw new InvalidAccessException();
//        }


    }

    @Override
    public void updateReport(ReportRequest request) {

    }

    @Override
    public Long deleteReport(Long reportId) {
        return null;
    }

    private boolean isAccessable(Report report) {
        return report.getReportType().getAccess().equals(Access.ADMIN);
    }
}
