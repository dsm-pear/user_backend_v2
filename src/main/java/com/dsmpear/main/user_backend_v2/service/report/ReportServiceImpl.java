package com.dsmpear.main.user_backend_v2.service.report;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Field;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Grade;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Type;
import com.dsmpear.main.user_backend_v2.entity.report.repository.ReportCustomRepositoryImpl;
import com.dsmpear.main.user_backend_v2.exception.InvalidAccessException;
import com.dsmpear.main.user_backend_v2.facade.report.ReportFacade;
import com.dsmpear.main.user_backend_v2.facade.user.UserFacade;
import com.dsmpear.main.user_backend_v2.mapper.*;
import com.dsmpear.main.user_backend_v2.payload.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportCustomRepositoryImpl reportCustomRepository;
    private final UserFacade userFacade;
    private final ReportFacade reportFacade;

    private final ReportMapper reportMapper;
    private final CommentMapper commentMapper;
    private final MemberMapper memberMapper;

    @Override
    public ReportContentResponse getReport(Long reportId) {
        Report report = reportFacade.createReport(reportId);

        List<ReportCommentsResponse> comments = report.getComments().stream().map(comment ->
                        commentMapper.entityToResponse(comment, comment.getUser().equals(userFacade.createAuthUser())))
                .collect(Collectors.toList());

        List<MemberResponse> members = report.getMembers().stream()
                .map(memberMapper::entityToResponse)
                .collect(Collectors.toList());

        if(!isAccessable(report)) {
            throw new InvalidAccessException();
        }

        return reportMapper.entityToContentResponse(report, isMine(report), comments, members);
    }

    @Override
    public ReportListResponse getReportList(Pageable pageable, Type type, Field field, Grade grade) {
        Page<Report> reportResponses = reportCustomRepository.findAllByAccessAndGradeAndFieldAndType(grade, field, type, pageable);

        return ReportListResponse.builder()
                .reportResponses(reportResponses.map(reportMapper::entityToResponse).toList())
                .totalElements(reportResponses.getTotalElements())
                .totalPages(reportResponses.getTotalPages())
                .build();
    }

    @Override
    public Long deleteReport(Long reportId) {
        Report report = reportFacade.createReport(reportId);
        if(!isMine(report)) throw new InvalidAccessException();
        reportRepository.delete(report);
        return reportId;
    }

    private boolean isAccessable(Report report) {
        return report.getReportType().getAccess().equals(Access.EVERY) || isMine(report);
    }

    private boolean isMine(Report report) {
        return report.getMembers().stream()
                .anyMatch(member -> member.getUser().equals(userFacade.createAuthUser()));
    }

}
