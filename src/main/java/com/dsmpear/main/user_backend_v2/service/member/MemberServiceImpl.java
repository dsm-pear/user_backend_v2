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


@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final AuthenticationFacade authenticationFacade;

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final ReportRepository reportRepository;

    @Override
    public void addMember(MemberRequest memberRequest) {

    }

    @Override
    public void deleteMember(Integer memberId) {
        if(!authenticationFacade.isLogin()) {
            throw new UserCannotAccessException();
        }

        User user = userRepository.findById(authenticationFacade.getEmail())
                .orElseThrow(UserNotFoundException::new);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        Report report = reportRepository.findById(member.getReport().getId())
                .orElseThrow(ReportNotFoundException::new);

        //삭제 요청을 한 유저가 이 보고서의 멤버인지 확인하기
        memberRepository.findByReportAndUser(report, user)
                .orElseThrow(UserNotMemberException::new);

        //삭제 요청을 한 유저와 삭제를 해야하는 멤버가 같으면 400
        if(user.getEmail().equals(member.getUser().getEmail())) {
            throw new UserEqualsMemberException();
        }
        memberRepository.delete(member);
    }

}
