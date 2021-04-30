package com.dsmpear.main.user_backend_v2.service.savereport;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.exception.InvalidAccessException;
import com.dsmpear.main.user_backend_v2.factory.ReportFactory;
import com.dsmpear.main.user_backend_v2.factory.UserFactory;
import com.dsmpear.main.user_backend_v2.mapper.MemberMapper;
import com.dsmpear.main.user_backend_v2.mapper.ReportMapper;
import com.dsmpear.main.user_backend_v2.mapper.ReportTypeMapper;
import com.dsmpear.main.user_backend_v2.payload.request.report.BaseReportRequest;
import com.dsmpear.main.user_backend_v2.payload.request.report.SoleReportRequest;
import com.dsmpear.main.user_backend_v2.payload.request.report.TeamReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SaveReportServiceImpl implements SaveReportService{

    private final ReportRepository reportRepository;

    private final UserFactory userFactory;
    private final ReportFactory reportFactory;

    private final ReportMapper reportMapper;
    private final ReportTypeMapper reportTypeMapper;
    private final MemberMapper memberMapper;

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
        Report report = reportMapper.requestToEntity(request, userFactory.createAuthUser());
        report.setReportType(reportTypeMapper.requestToEntity(request, report));

        updateMember(request, report);

        return reportRepository.save(report);
    }

    private <R extends BaseReportRequest>Long updateReportContent(R request, Long reportId) {
        Report report = reportFactory.create(reportId);

        if(!isMine(report)) throw new InvalidAccessException();

        report.update(request);
        report.getReportType().update(request);

        updateMember(request, report);

        report.addLanguage(request.getLanguages());

        return reportId;
    }

    private <R extends BaseReportRequest> void updateMember(R request, Report report) {
        report.getMembers().clear();
        if((isTeamRequest(request))) {
            report.getMembers().addAll(((TeamReportRequest) request)
                    .getMembers()
                    .stream().filter(member -> !member.equals(userFactory.createAuthUser().getEmail()))
                    .map(member ->
                            memberMapper.getEntity(userFactory.createUser(member), report))
                    .collect(Collectors.toList()));
        }
        report.getMembers().add(memberMapper.getEntity(userFactory.createAuthUser(), report));
    }

    private boolean isMine(Report report) {
        return report.getMembers().stream()
                .anyMatch(member -> member.getUser().equals(userFactory.createAuthUser()));
    }

}
