package com.dsmpear.main.user_backend_v2.service.profile;

import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.member.MemberRepository;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.report.repository.ReportCustomRepositoryImpl;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.exception.UserNotFoundException;
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

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final ReportCustomRepositoryImpl reportCustomRepository;

    @Override
    public ProfilePageResponse getProfile(String email) {
        User user = userRepository.findById(email)
                .orElseThrow(UserNotFoundException::new);

        return ProfilePageResponse.builder()
                .userName(user.getName())
                .userEmail(user.getEmail())
                .gitHub(user.getGitHub())
                .selfIntro(user.getSelfIntro())
                .build();
    }

    @Override
    public ProfileReportsResponse getReport(String userEmail, Pageable page) {

        User user = userRepository.findById(userEmail)
                .orElseThrow(UserNotFoundException::new);
        Page<Report> reportPage = reportCustomRepository.findAllByMembersContainsAndIsAcceptedAndIsSubmittedTrueAndReportTypeAccessOrderByReportIdDesc(user, Access.EVERY, page);
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
