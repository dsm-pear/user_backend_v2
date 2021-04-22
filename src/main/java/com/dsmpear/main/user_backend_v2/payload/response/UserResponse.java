package com.dsmpear.main.user_backend_v2.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private String name;

    private String email;

}
