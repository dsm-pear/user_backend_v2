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
public class UserFactory {

    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;

    public User createAuthUser() {
        return getUser(authenticationFacade.getEmail());
    }

    public User createUser(String email) {
        return getUser(email);
    }

    private User getUser(String email) {
        System.out.println(email);
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

}
