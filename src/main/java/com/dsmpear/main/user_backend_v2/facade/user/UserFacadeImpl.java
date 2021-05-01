package com.dsmpear.main.user_backend_v2.facade.user;

import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.exception.UserCannotAccessException;
import com.dsmpear.main.user_backend_v2.exception.UserNotFoundException;
import com.dsmpear.main.user_backend_v2.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;

    public User createAuthUser() {
        if(!authenticationFacade.isLogin()) {
            throw new UserCannotAccessException();
        }
        return getUser(authenticationFacade.getEmail());
    }

    public User createUser(String email) {
        return getUser(email);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

}
