package com.dsmpear.main.user_backend_v2.controller;

import com.dsmpear.main.user_backend_v2.payload.request.SignInRequest;
import com.dsmpear.main.user_backend_v2.payload.response.AccessTokenResponse;
import com.dsmpear.main.user_backend_v2.payload.response.TokenResponse;
import com.dsmpear.main.user_backend_v2.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public TokenResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }

    @PutMapping
    public AccessTokenResponse tokenRefresh(@RequestHeader(name = "X-Refresh-Token") String token) {
        return authService.tokenRefresh(token);
    }

}
