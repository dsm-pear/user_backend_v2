package com.dsmpear.main.user_backend_v2.service.auth;

import com.dsmpear.main.user_backend_v2.entity.refreshtoken.RefreshToken;
import com.dsmpear.main.user_backend_v2.entity.refreshtoken.RefreshTokenRepository;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.exception.InvalidTokenException;
import com.dsmpear.main.user_backend_v2.exception.UserNotFoundException;
import com.dsmpear.main.user_backend_v2.payload.request.SignInRequest;
import com.dsmpear.main.user_backend_v2.payload.response.AccessTokenResponse;
import com.dsmpear.main.user_backend_v2.payload.response.TokenResponse;
import com.dsmpear.main.user_backend_v2.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Value("${auth.jwt.exp.refresh}")
    private Long refreshExp;

    @Override
    public TokenResponse signIn(SignInRequest request) {
        userRepository.findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .orElseThrow(UserNotFoundException::new);

        RefreshToken refreshToken = refreshTokenRepository.save(
                RefreshToken.builder()
                        .refreshExp(refreshExp)
                        .refreshToken(jwtTokenProvider.generateRefreshToken(request.getEmail()))
                        .email(request.getEmail())
                        .build());

        return TokenResponse.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(request.getEmail()))
                .refreshToken(refreshToken.getRefreshToken())
                .build();

    }

    @Override
    @Transactional
    public AccessTokenResponse tokenRefresh(String token) {
        if (jwtTokenProvider.isRefreshToken(token)) throw new InvalidTokenException();

        return refreshTokenRepository.findByRefreshToken(token)
                .map(refreshToken -> refreshToken.update(refreshExp))
                .map(refreshToken -> jwtTokenProvider.generateAccessToken(refreshToken.getEmail()))
                .map(accessToken -> new AccessTokenResponse(accessToken))
                .orElseThrow(InvalidTokenException::new);
    }


}
