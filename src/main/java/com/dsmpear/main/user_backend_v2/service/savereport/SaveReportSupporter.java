package com.dsmpear.main.user_backend_v2.service.savereport;

import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.repository.ReportRepository;
import com.dsmpear.main.user_backend_v2.exception.InvalidAccessException;
import com.dsmpear.main.user_backend_v2.facade.report.ReportFacade;
import com.dsmpear.main.user_backend_v2.facade.user.UserFacade;
import com.dsmpear.main.user_backend_v2.mapper.ReportMapper;
import com.dsmpear.main.user_backend_v2.mapper.ReportTypeMapper;
import com.dsmpear.main.user_backend_v2.payload.request.report.BaseReportRequest;
import com.dsmpear.main.user_backend_v2.payload.request.report.SoleReportRequest;
import com.dsmpear.main.user_backend_v2.payload.request.report.TeamReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SaveReportSupporter {

    // facades
    private final UserFacade userFacade;
    private final ReportFacade reportFacade;

    // mappers
    private final ReportTypeMapper reportTypeMapper;
    private final ReportMapper reportMapper;

    // repositories
    private final ReportRepository reportRepository;

    private <R extends BaseReportRequest> boolean isTeamRequest(R request) {
        return !(request instanceof SoleReportRequest);
    }

    public <R extends BaseReportRequest> Report saveReport(R request) {
        Report report = setTeamName(reportMapper.requestToEntity(request, userFacade.createAuthUser()), request);
        report.setReportType(reportTypeMapper.requestToEntity(request, report));

        updateMember(request, report);

        return reportRepository.save(report);
    }

    public <R extends BaseReportRequest> Long updateReportContent(R request, Long reportId) {
        Report report = reportFacade.createReport(reportId);

        if(!userFacade.isMine(report)) throw new InvalidAccessException();

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
            members.addAll(getTeamReportRequest(request).getMembers()
                    .stream().map(member -> buildMember(member, report))
                    .collect(Collectors.toList()));
        }

        report.addMember(members
                .stream().distinct()
                .collect(Collectors.toList()));

    }

    private Member buildMember(String email, Report report) {
        return Member.builder()
                .user(userFacade.createUser(email))
                .report(report)
                .build();
    }

    private <T extends BaseReportRequest> Report setTeamName(Report report, T request) {
        return report.toBuilder()
                .teamName(isTeamRequest(request) ? getTeamReportRequest(request).getTeamName() : null)
                .build();
    }

    private TeamReportRequest getTeamReportRequest(BaseReportRequest request) {
        return (TeamReportRequest) request;
    }
}
