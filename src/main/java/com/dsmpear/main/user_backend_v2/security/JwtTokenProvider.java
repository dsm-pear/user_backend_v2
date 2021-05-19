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
import org.springframework.security.core.context.SecurityContextHolder;
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

    private static final String HEADER = "Authorization";

    private static final String PREFIX = "Bearer";

    private final AuthDetailsService authDetailsService;

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration * 1000))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, encoding())
                .setSubject(email)
                .claim("type", "access")
                .claim("role", "user")
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration * 1000))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, encoding())
                .setSubject(email)
                .claim("type", "refresh")
                .compact();
    }

    public void tokenFilter(HttpServletRequest request) {
        String token = resolveToken(request);

        if(token != null && validateToken(token)) {
            Authentication authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(HEADER);

        if(bearer != null && bearer.startsWith(PREFIX)) {
            return bearer.substring(7);
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            return getTokenBody(token).getExpiration().after(new Date());
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

    private String getEmail(String token) {
        try {
            return getTokenBody(token).getSubject();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    private Authentication getAuthentication(String token) {
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
