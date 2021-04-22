package com.dsmpear.main.user_backend_v2.security.auth;

import com.dsmpear.main.user_backend_v2.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUser() {
        return this.getAuthentication().getName();
    }

    public boolean isLogin() {
        return this.getAuthentication() != null;
    }
}
