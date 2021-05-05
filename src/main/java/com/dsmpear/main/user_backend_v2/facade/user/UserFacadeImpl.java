package com.dsmpear.main.user_backend_v2.facade.user;

import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.exception.UserCannotAccessException;
import com.dsmpear.main.user_backend_v2.exception.UserNotFoundException;
import com.dsmpear.main.user_backend_v2.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserFacadeImpl implements UserFacade {

    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;

    @Override
    public User createAuthUser() {
        if(!authenticationFacade.isLogin()) {
            throw new UserCannotAccessException();
        }
        return getUser(authenticationFacade.getEmail());
    }

    @Override
    public User createUser(String email) {
        return getUser(email);
    }

    @Override
    public boolean isMine(Report report) {
        return report.getMembers().stream()
                .map(Member::getUser)
                .anyMatch(member -> member.equals(createAuthUser()));
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

}
