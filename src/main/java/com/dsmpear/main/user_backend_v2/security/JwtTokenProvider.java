package com.dsmpear.main.user_backend_v2.security;

import com.dsmpear.main.user_backend_v2.exception.InvalidTokenException;
import com.dsmpear.main.user_backend_v2.security.auth.AuthDetails;
import com.dsmpear.main.user_backend_v2.security.auth.AuthDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${auth.jwt.secret}")
    private String secretKey;

    @Value("${auth.jwt.exp.access}")
    private Long accessTokenExpiration;

    @Value("${auth.jwt.exp.refresh}")
    private Long refreshTokenExpiration;

    @Value("${auth.jwt.header}")
    private String header;

    @Value("${auth.jwt.prefix}")
    private String prefix;

    private final AuthDetailsService authDetailsService;

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, encoding())
                .setSubject(email)
                .claim("type", "access")
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, encoding())
                .setSubject(email)
                .claim("type", "refresh")
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(header);

        if(bearer != null && bearer.startsWith(prefix)) {
            return bearer.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    public boolean isRefreshToken(String token) {
        try {
            return getTokenBody(token).get("type").equals("refresh");
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    public String getEmail(String token) {
        try {
            return getTokenBody(token).getSubject();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    public Authentication getAuthentication(String token) {
        AuthDetails authDetails = authDetailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(authDetails, "", authDetails.getAuthorities());
    }

    private String encoding() {
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    private Claims getTokenBody(String token) {
        return Jwts.parser().setSigningKey(encoding())
                .parseClaimsJws(token).getBody();
    }
}
