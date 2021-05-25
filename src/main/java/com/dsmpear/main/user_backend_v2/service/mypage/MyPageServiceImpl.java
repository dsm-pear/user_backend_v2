package com.dsmpear.main.user_backend_v2.service.mypage;

import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.facade.user.UserFacade;
import com.dsmpear.main.user_backend_v2.payload.response.MyPageReportResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ProfilePageResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ProfileReportsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MyPageServiceImpl implements MyPageService {

    private final UserRepository userRepository;

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
        List<Member> members = userFacade.createAuthUser().getMember();
        List<MyPageReportResponse> myPageReportResponses = new ArrayList<>();

        for(Member member : members) {
            myPageReportResponses.add(
                    MyPageReportResponse.builder()
                            .reportId(member.getReport().getId())
                            .title(member.getReport().getTitle())
                            .createdAt(member.getReport().getCreatedAt())
                            .type(member.getReport().getReportType().getType())
                            .isAccepted(member.getReport().getStatus().getIsAccepted())
                            .isRejected(member.getReport().getComment() != null)
                            .isSubmitted(member.getReport().getStatus().getIsSubmitted())
                            .build()
            );
        }

        Collections.reverse(myPageReportResponses);

        PageImpl<MyPageReportResponse> pageReportResponses = new PageImpl<>(myPageReportResponses, page, myPageReportResponses.size());

        return ProfileReportsResponse.builder()
                .totalPages(pageReportResponses.getTotalPages())
                .myPageReportResponses(myPageReportResponses)
                .build();
    }

}
