package com.dsmpear.main.user_backend_v2.service.member;

import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.member.MemberRepository;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.exception.*;
import com.dsmpear.main.user_backend_v2.payload.request.MemberRequest;
import com.dsmpear.main.user_backend_v2.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final AuthenticationFacade authenticationFacade;

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final ReportRepository reportRepository;

    @Override
    public void addMember(MemberRequest memberRequest) {
        User user = getUser();

        Report report = reportRepository.findById(memberRequest.getReportId())
                .orElseThrow(ReportNotFoundException::new);

        if(report.getIsSubmitted()) { //보고서가 이미 제출된 상태라면 멤버 수정 불가
            throw new ReportAlreadySubmittedException();
        }

        memberRepository.findByReportAndUser(report, user)
                .orElseThrow(UserNotMemberException::new);

        User requestedUser = userRepository.findById(memberRequest.getUserEmail())
                .orElseThrow(UserNotMemberException::new);

        memberRepository.findByReportAndUser(report, requestedUser)
                .ifPresent(member -> {throw new UserAlreadyIncludeAsMemberException();});

        memberRepository.save(
                Member.builder()
                        .user(requestedUser)
                        .report(report)
                        .build()
        );

    }

    @Override
    public void deleteMember(Integer memberId) {
        User user = getUser();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        Report report = reportRepository.findById(member.getReport().getId())
                .orElseThrow(ReportNotFoundException::new);

        if(report.getIsSubmitted()) { //보고서가 이미 제출된 상태라면 멤버 수정 불가
            throw new ReportAlreadySubmittedException();
        }

        //삭제 요청을 한 유저가 이 보고서의 멤버인지 확인하기
        memberRepository.findByReportAndUser(report, user)
                .orElseThrow(UserNotMemberException::new);

        //삭제 요청을 한 유저와 삭제를 해야하는 멤버가 같으면 400
        if(user.getEmail().equals(member.getUser().getEmail())) {
            throw new UserEqualsMemberException();
        }
        memberRepository.delete(member);
    }

    private User getUser() {
        if(!authenticationFacade.isLogin()) {
            throw new UserCannotAccessException();
        }

        return userRepository.findById(authenticationFacade.getEmail())
                .orElseThrow(UserNotFoundException::new);
    }

}
