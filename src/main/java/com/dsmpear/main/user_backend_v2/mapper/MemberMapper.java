package com.dsmpear.main.user_backend_v2.mapper;

import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.payload.response.MemberResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface MemberMapper {

    @Mapping(source = "member.user.name", target = "memberName")
    @Mapping(source = "member.user.email", target = "memberEmail")
    @Mapping(source = "member.id", target = "memberId")
    MemberResponse entityToResponse(Member member);

}
