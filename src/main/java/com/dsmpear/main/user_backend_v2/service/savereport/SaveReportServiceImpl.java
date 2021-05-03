package com.dsmpear.main.user_backend_v2.service.savereport;

import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.member.MemberRepository;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.exception.InvalidAccessException;
import com.dsmpear.main.user_backend_v2.facade.report.ReportFacade;
import com.dsmpear.main.user_backend_v2.facade.user.UserFacade;
import com.dsmpear.main.user_backend_v2.mapper.ReportMapper;
import com.dsmpear.main.user_backend_v2.mapper.ReportTypeMapper;
import com.dsmpear.main.user_backend_v2.payload.request.report.BaseReportRequest;
import com.dsmpear.main.user_backend_v2.payload.request.report.SoleReportRequest;
import com.dsmpear.main.user_backend_v2.payload.request.report.TeamReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SaveReportServiceImpl implements SaveReportService{

    private final ReportRepository reportRepository;

    private final UserFacade userFacade;
    private final ReportFacade reportFacade;

    private final ReportMapper reportMapper;
    private final ReportTypeMapper reportTypeMapper;

    @Override
    @Transactional
    public Long saveSoleReport(SoleReportRequest request) {
        return saveReport(request).getId();
    }

    @Override
    @Transactional
    public Long tempSaveSoleReport(SoleReportRequest request, Long reportId) {
        return updateReportContent(request, reportId);
    }

    @Override
    @Transactional
    public Long updateSoleReport(SoleReportRequest request, Long reportId) {
        return updateReportContent(request, reportId);
    }

    @Override
    @Transactional
    public Long saveTeamReport(TeamReportRequest request) {
        return saveReport(request).getId();
    }

    @Override
    @Transactional
    public Long tempSaveTeamReport(TeamReportRequest request, Long reportId) {
        return updateReportContent(request, reportId);
    }

    @Override
    @Transactional
    public Long updateTeamReport(TeamReportRequest request, Long reportId) {
        return updateReportContent(request, reportId);
    }

    private <R extends BaseReportRequest> boolean isTeamRequest(R request) {
        return !(request instanceof SoleReportRequest);
    }

    private <R extends BaseReportRequest> Report saveReport(R request) {
        Report report = reportMapper.requestToEntity(request, userFacade.createAuthUser());
        report.setReportType(reportTypeMapper.requestToEntity(request, report));

        updateMember(request, report);

        return reportRepository.save(report);
    }

    private <R extends BaseReportRequest>Long updateReportContent(R request, Long reportId) {
        Report report = reportFacade.createReport(reportId);

        if(!isMine(report)) throw new InvalidAccessException();

        report.update(request);

        updateMember(request, report);

        report.getReportType().update(request);

        report.addLanguage(request.getLanguages());

        return report.getId();
    }

    private <R extends BaseReportRequest> void updateMember(R request, Report report) {
        List<Member> members = new ArrayList<>(
                Collections.singletonList(buildMember(userFacade.createAuthUser().getEmail(), report)));

        if(isTeamRequest(request)) {
            members.addAll(((TeamReportRequest)request).getMembers()
                    .stream().distinct()
                    .filter(user -> !user.equals(userFacade.createAuthUser().getEmail()))
                    .map(member -> buildMember(member, report))
                    .collect(Collectors.toList()));
        }
        
        report.addMember(members);

    }

    private Member buildMember(String email, Report report) {
        return Member.builder()
                .user(userFacade.createUser(email))
                .report(report)
                .build();
    }

    private boolean isMine(Report report) {
        return report.getMembers().stream()
                .map(Member::getUser)
                .anyMatch(member -> member.equals(userFacade.createAuthUser()));
    }

}
