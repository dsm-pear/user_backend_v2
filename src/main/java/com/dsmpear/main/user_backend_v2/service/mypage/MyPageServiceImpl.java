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
import com.dsmpear.main.user_backend_v2.facade.report.ReportFacade;
import com.dsmpear.main.user_backend_v2.facade.user.UserFacade;
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

    private final UserFacade userFacade;

    @Override
    public ProfilePageResponse getMyPage() {
        User user = userFacade.createAuthUser();

        return ProfilePageResponse.builder()
                .userName(user.getName())
                .userEmail(user.getEmail())
                .selfIntro(user.getSelfIntro())
                .gitHub(user.getGitHub())
                .build();
    }

    @Override
    public void setSelfIntro(String intro, String gitHub) {
        User user = userFacade.createAuthUser();
        user.updateInfo(intro, gitHub);
        userRepository.save(user);
    }

    @Override
    public ProfileReportsResponse getReport(Pageable page) {
        User user = userFacade.createAuthUser();
        List<Member> members = memberRepository.findAllByUser(user);
        List<MyPageReportResponse> myPageReportResponses = new ArrayList<>();

        for(Member member : members) {
            myPageReportResponses.add(
                    MyPageReportResponse.builder()
                            .reportId(member.getReport().getId())
                            .title(member.getReport().getTitle())
                            .createdAt(member.getReport().getCreatedAt())
                            .type(member.getReport().getReportType().getType())
                            .isRejected(member.getReport().getComment() != null)
                            .isSubmitted(member.getReport().getIsSubmitted())
                            .build()
            );
        }

        long totalElements = members.size();
        int totalPages = members.size() + (members.size()%page.getPageSize()>0?1:0);

        return ProfileReportsResponse.builder()
                .totalElements(totalElements)
                .totalPages(totalPages)
                .myPageReportResponses(myPageReportResponses)
                .build();
    }

}
