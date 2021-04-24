package com.dsmpear.main.user_backend_v2.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {

    private Long reportId;

    private String userEmail;

}
