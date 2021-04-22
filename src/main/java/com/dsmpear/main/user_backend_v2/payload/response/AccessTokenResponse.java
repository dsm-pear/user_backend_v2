package com.dsmpear.main.user_backend_v2.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccessTokenResponse {

    @JsonProperty("access_token")
    private final String accessToken;

}
