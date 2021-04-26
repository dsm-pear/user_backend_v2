package com.dsmpear.main.user_backend_v2.service.report;

import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Field;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Grade;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Type;
import com.dsmpear.main.user_backend_v2.entity.report.repository.ReportCustomRepositoryImpl;
import com.dsmpear.main.user_backend_v2.exception.InvalidAccessException;
import com.dsmpear.main.user_backend_v2.exception.ReportNotFoundException;
import com.dsmpear.main.user_backend_v2.factory.ReportFactory;
import com.dsmpear.main.user_backend_v2.factory.UserFactory;
import com.dsmpear.main.user_backend_v2.mapper.*;
import com.dsmpear.main.user_backend_v2.payload.request.ReportRequest;
import com.dsmpear.main.user_backend_v2.payload.response.*;
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
    private final ReportFactory reportFactory;

    private final ReportMapper reportMapper;
    private final ReportTypeMapper reportTypeMapper;
    private final CommentMapper commentMapper;
    private final MemberMapper memberMapper;

    @Override
    @Transactional
    public Long createReport(ReportRequest request) {
        Report report = reportMapper.requestToEntity(request, userFactory.createAuthUser());
        report.setReportType(reportTypeMapper.requestToEntity(request, report));
        updateMember(report, request.getMembers());
        report.addMember(memberMapper.getEntity(userFactory.createAuthUser(), report));

        return reportRepository.save(report).getId();
    }

    @Override
    public ReportContentResponse getReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(ReportNotFoundException::new);

        List<ReportCommentsResponse> comments = report.getComments().stream().map(comment ->
                        commentMapper.entityToResponse(comment, comment.getUser().equals(userFactory.createAuthUser())))
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
    @Transactional
    public Long updateReport(Long reportId, ReportRequest request) {
        return updateReportContent(request, reportId);
    }

    @Override
    @Transactional
    public Long temporaryStorage(ReportRequest request, Long reportId) {
        return updateReportContent(request, reportId);
    }

    @Override
    public Long deleteReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(ReportNotFoundException::new);
        if(!isMine(report)) throw new InvalidAccessException();
        reportRepository.delete(report);
        return reportId;
    }

    private Long updateReportContent(ReportRequest request, Long reportId) {
        Report report = reportFactory.create(reportId);
        if(!isMine(report)) throw new InvalidAccessException();
        report.update(request);
        report.getReportType().update(request);
        updateMember(report, request.getMembers());
        report.getLanguages()
                .forEach(report.getLanguages()::add);
        return reportId;
    }

    private boolean isAccessable(Report report) {
        return report.getReportType().getAccess().equals(Access.EVERY) || isMine(report);
    }

    private boolean isMine(Report report) {
        return report.getMembers().stream()
                .anyMatch(member -> member.getUser().equals(userFactory.createAuthUser()));
    }

    private void updateMember(Report report, List<String> members) {
        report.setMembers(members.stream()
                .map(member -> Member.builder()
                        .user(userFactory.createUser(member))
                        .report(report).build()).collect(Collectors.toList()));
    }
}
