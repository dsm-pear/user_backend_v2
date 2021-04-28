package com.dsmpear.main.user_backend_v2.service.savereport;

import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.reporttype.ReportType;
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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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

    private void updateMember(Report report, List<String> members) {
        System.out.println("멤버 리스트");
        members.forEach(System.out::println);

        System.out.println("보고서의 멤버 size");
        System.out.println(report.getMembers().size());
        report.getMembers().clear();
        System.out.println("클리어 후 : " + report.getMembers().size());
        report.getMembers().addAll(members.stream()
                .map(member -> Member.builder()
                        .user(userFactory.createUser(member))
                        .report(report).build()).collect(Collectors.toList()));
    }

    private <R extends BaseReportRequest> boolean isSoleRequest(R request) {
        return request instanceof SoleReportRequest;
    }

    private <R extends BaseReportRequest> Report saveReport(R request) {
        Report report = reportMapper.requestToEntity(request, userFactory.createAuthUser());
        report.setReportType(reportTypeMapper.requestToEntity(request, report));

        if(!(request instanceof SoleReportRequest)) {

            updateMember(report, ((TeamReportRequest) request).getMembers());
        } else {
            report.addMember(memberMapper.getEntity(userFactory.createAuthUser(), report));
        }

        return reportRepository.save(report);
    }

    private <R extends BaseReportRequest>Long updateReportContent(R request, Long reportId) {
        Report report = reportFactory.create(reportId);

        if(!isMine(report)) throw new InvalidAccessException();

        report.update(request);
        report.getReportType().update(request);

        if(!isSoleRequest(request)) updateMember(report, ((TeamReportRequest)request).getMembers());

        report.addLanguage(request.getLanguages());

        return reportId;
    }

    private boolean isMine(Report report) {
        return report.getMembers().stream()
                .anyMatch(member -> member.getUser().equals(userFactory.createAuthUser()));
    }
}
