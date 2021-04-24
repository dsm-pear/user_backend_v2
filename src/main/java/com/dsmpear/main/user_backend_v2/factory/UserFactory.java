package com.dsmpear.main.user_backend_v2.factory;

import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.exception.InvalidAccessException;
import com.dsmpear.main.user_backend_v2.exception.UserNotFoundException;
import com.dsmpear.main.user_backend_v2.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactory implements Factory<UserRepository, User> {

    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;

    @Override
    public User create(String value) {
        return getUser(value == null ? "" : value);
    }

    public User createAuthUser() {
        return getUser(authenticationFacade.getEmail());
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

}
