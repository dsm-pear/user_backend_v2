package com.dsmpear.main.user_backend_v2.service.auth;

import com.dsmpear.main.user_backend_v2.payload.request.SignInRequest;
import com.dsmpear.main.user_backend_v2.payload.response.AccessTokenResponse;
import com.dsmpear.main.user_backend_v2.payload.response.TokenResponse;

public interface AuthService {
    TokenResponse signIn(SignInRequest request);
    AccessTokenResponse tokenRefresh(String token);
}
