package com.dsmpear.main.user_backend_v2.service.mypage;

import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.member.MemberRepository;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.exception.NoticeNotFoundException;
import com.dsmpear.main.user_backend_v2.exception.UserCannotAccessException;
import com.dsmpear.main.user_backend_v2.exception.UserNotFoundException;
import com.dsmpear.main.user_backend_v2.payload.response.MyPageReportResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ProfilePageResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ProfileReportsResponse;
import com.dsmpear.main.user_backend_v2.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MyPageServiceImpl implements MyPageService {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public ProfilePageResponse getMyPage() {
        User user = getUser();

        return ProfilePageResponse.builder()
                .userName(user.getName())
                .userEmail(user.getEmail())
                .selfIntro(user.getSelfIntro())
                .gitHub(user.getGitHub())
                .build();
    }

    @Override
    public void setSelfIntro(String intro, String gitHub) {
        User user = getUser();
        user.updateInfo(intro, gitHub);
        userRepository.save(user);
    }

    @Override
    public ProfileReportsResponse getReport(Pageable page) {
        User user = getUser();
        List<Member> members = memberRepository.findAllByUser(user);
        List<MyPageReportResponse> myPageReportResponses = new ArrayList<>();

        for(Member member : members) {
            Report report = reportRepository.findById(member.getReport().getId())
                    .orElseThrow(NoticeNotFoundException::new);

            myPageReportResponses.add(
                    MyPageReportResponse.builder()
                            .reportId(report.getId())
                            .title(report.getTitle())
                            .createdAt(report.getCreatedAt())
                            .type(report.getReportType().getType())
                            .isRejected(report.getComment() != null)
                            .isAccepted(report.getIsAccepted())
                            .isSubmitted(report.getIsSubmitted())
                            .build()
            );
        }

        long totalElements = members.size();
        int totalPages = members.size() + (members.size()%page.getPageSize()>0?1:0);

        return ProfileReportsResponse.builder()
                .totalElements(totalElements)
                .totalPages(totalPages)
                .myProfileReportResponses(myPageReportResponses)
                .build();
    }

    private User getUser() {
        if(!authenticationFacade.isLogin()) {
            throw new UserCannotAccessException();
        }
        return userRepository.findById(authenticationFacade.getEmail())
                .orElseThrow(UserNotFoundException::new);
    }

}
