package com.dsmpear.main.user_backend_v2.service.profile;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.report.repository.ReportCustomRepositoryImpl;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.facade.user.UserFacade;
import com.dsmpear.main.user_backend_v2.payload.response.ProfilePageResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ProfileReportResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ProfileReportsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    private final ReportCustomRepositoryImpl reportCustomRepository;

    private final UserFacade userFacade;

    @Override
    public ProfilePageResponse getProfile(String email) {
        User user = userFacade.createUser(email);

        return ProfilePageResponse.builder()
                .userName(user.getName())
                .userEmail(user.getEmail())
                .gitHub(user.getGitHub())
                .selfIntro(user.getSelfIntro())
                .build();
    }

    @Override
    public ProfileReportsResponse getReport(String userEmail, Pageable page) {
        User user = userFacade.createUser(userEmail);

        Page<Report> reportPage = reportCustomRepository.findAllByMembersContainsAndIsAcceptedAndIsSubmittedTrueAndReportTypeAccessOrderByReportIdDesc(user, page);
        List<ProfileReportResponse> reportResponses = new ArrayList<>();

        for(Report report : reportPage) {
            reportResponses.add(
                    ProfileReportResponse.builder()
                            .reportId(report.getId())
                            .title(report.getTitle())
                            .createdAt(report.getCreatedAt())
                            .type(report.getReportType().getType())
                            .build()
            );
        }

        return ProfileReportsResponse.builder()
                .totalElements(reportPage.getTotalElements())
                .totalPages(reportPage.getTotalPages())
                .profileReportResponses(reportResponses)
                .build();
    }

}
