package com.dsmpear.main.user_backend_v2.service.user;

import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.entity.verifynumber.VerifyNumberRepository;
import com.dsmpear.main.user_backend_v2.entity.verifyuser.VerifyUser;
import com.dsmpear.main.user_backend_v2.entity.verifyuser.VerifyUserRepository;
import com.dsmpear.main.user_backend_v2.exception.NumberNotFoundException;
import com.dsmpear.main.user_backend_v2.exception.UserAlreadyExist;
import com.dsmpear.main.user_backend_v2.exception.UserCannotAccessException;
import com.dsmpear.main.user_backend_v2.payload.request.EmailVerifyRequest;
import com.dsmpear.main.user_backend_v2.payload.request.RegisterRequest;
import com.dsmpear.main.user_backend_v2.payload.response.UserResponse;
import com.dsmpear.main.user_backend_v2.payload.response.UsersResponse;
import com.dsmpear.main.user_backend_v2.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final VerifyUserRepository verifyUserRepository;
    private final VerifyNumberRepository verifyNumberRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public void register(RegisterRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresentOrElse(user -> {throw new UserAlreadyExist();},
                        () -> verifyUserRepository.findByEmail(request.getEmail())
                                .map(verifyNumber -> userRepository.save(
                                        User.builder()
                                                .name(request.getName())
                                                .email(request.getEmail())
                                                .password(passwordEncoder.encode(request.getPassword()))
                                                .build()
                                )));
    }

    @Override
    public void verify(EmailVerifyRequest request) {
        verifyNumberRepository.findByEmail(request.getEmail())
                .filter(verifyNumber -> verifyNumber.verifyNumber(request.getNumber()))
                .map(verifyNumber -> verifyUserRepository.save(new VerifyUser(request.getEmail())))
                .orElseThrow(NumberNotFoundException::new);
    }

    @Override
    public UsersResponse getUserList(String name) {
        if(!authenticationFacade.isLogin()) {
            throw new UserCannotAccessException();
        }

        List<User> users = userRepository.findAllByNameContainingOrderByName(name);
        List<UserResponse> userResponses = new ArrayList<>();

        for(User user : users) {
            userResponses.add(
                    UserResponse.builder()
                            .email(user.getEmail())
                            .name(user.getName())
                            .build()
            );
        }

        return UsersResponse.builder()
                .totalElements(users.size())
                .userResponses(userResponses)
                .build();
    }

}
