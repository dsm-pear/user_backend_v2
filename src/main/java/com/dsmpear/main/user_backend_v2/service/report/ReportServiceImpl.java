package com.dsmpear.main.user_backend_v2.service.report;

import com.dsmpear.main.user_backend_v2.entity.comment.Comment;
import com.dsmpear.main.user_backend_v2.entity.comment.CommentRepository;
import com.dsmpear.main.user_backend_v2.entity.language.Language;
import com.dsmpear.main.user_backend_v2.entity.notice.Notice;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Field;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Grade;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Type;
import com.dsmpear.main.user_backend_v2.entity.report.repository.ReportCustomRepositoryImpl;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.exception.InvalidAccessException;
import com.dsmpear.main.user_backend_v2.exception.ReportNotFoundException;
import com.dsmpear.main.user_backend_v2.factory.UserFactory;
import com.dsmpear.main.user_backend_v2.mapper.*;
import com.dsmpear.main.user_backend_v2.payload.request.ReportRequest;
import com.dsmpear.main.user_backend_v2.payload.response.*;
import com.dsmpear.main.user_backend_v2.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportCustomRepositoryImpl reportCustomRepository;
    private final UserFactory userFactory;
    private final AuthenticationFacade authenticationFacade;

    private final ReportMapper reportMapper;
    private final ReportTypeMapper reportTypeMapper;
    private final LanguageMapper languageMapper;
    private final CommentMapper commentMapper;
    private final MemberMapper memberMapper;

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
        Report report = reportRepository.findById(reportId)
                .orElseThrow(ReportNotFoundException::new);

        List<ReportCommentsResponse> comments = report.getComments().stream().map(comment ->
                        commentMapper.entityToResponse(comment, comment.getUser().equals(userFactory.createAuthUser())))
                .collect(Collectors.toList());

        if(isAccessable(report)) {
            throw new InvalidAccessException();
        }

        return ReportContentResponse.builder()
                .access(report.getReportType().getAccess())
                .comment(report.getComment())
                .createdAt(report.getCreatedAt())
                .description(report.getDescription())
                .field(report.getReportType().getField())
                .grade(report.getReportType().getGrade())
                .fileId(report.getReportFile().getId())
                .type(report.getReportType().getType())
                .fileName(report.getReportFile().getFileName())
                .isMine(isMine(report))
                .teamName(report.getTeamName())
                .title(report.getTitle())
                .comments(comments)
                .languages(report.getLanguages().stream()
                        .map(language -> language.getLanguage())
                        .collect(Collectors.toList()))
                .member(report.getMembers().stream()
                        .map(member -> memberMapper.entityToResponse(member))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public ReportListResponse getReportList(Pageable pageable, Type type, Field field, Grade grade) {
        Page<Report> reportResponses = reportCustomRepository.findAllByAccessAndGradeAndFieldAndType(grade, field, type, pageable);

        return ReportListResponse.builder()
                .reportResponses(reportResponses.map(report -> reportMapper.entityToResponse(report)).toList())
                .totalElements(reportResponses.getTotalElements())
                .totalPages(reportResponses.getTotalPages())
                .build();
    }

    @Override
    @Transactional
    public Long updateReport(Long reportId, ReportRequest request) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(ReportNotFoundException::new);
        if(isMine(report)) throw new InvalidAccessException();
        report.update(request);
        report.getReportType().update(request);
        return reportId;
    }

    @Override
    public Long deleteReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(ReportNotFoundException::new);
        isMine(report);
        reportRepository.delete(report);
        return reportId;
    }

    private boolean isAccessable(Report report) {
        return report.getReportType().getAccess().equals(Access.EVERY) || isMine(report);
    }

    private boolean isMine(Report report) {
        if(authenticationFacade.isLogin()) return false;
        return report.getMembers().stream()
                .anyMatch(member -> member.getUser().equals(userFactory.createAuthUser()));
    }
}
