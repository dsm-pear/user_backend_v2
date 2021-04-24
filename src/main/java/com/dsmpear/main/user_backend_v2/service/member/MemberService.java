package com.dsmpear.main.user_backend_v2.service.member;

import com.dsmpear.main.user_backend_v2.payload.request.MemberRequest;

public interface MemberService {
    void addMember(MemberRequest memberRequest);
    void deleteMember(Integer memberId);
}
