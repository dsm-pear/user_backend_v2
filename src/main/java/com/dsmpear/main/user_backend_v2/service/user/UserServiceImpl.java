package com.dsmpear.main.user_backend_v2.service.user;

import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.entity.verifynumber.VerifyNumberRepository;
import com.dsmpear.main.user_backend_v2.entity.verifyuser.VerifyUser;
import com.dsmpear.main.user_backend_v2.entity.verifyuser.VerifyUserRepository;
import com.dsmpear.main.user_backend_v2.exception.NumberNotFoundException;
import com.dsmpear.main.user_backend_v2.exception.UserAlreadyExist;
import com.dsmpear.main.user_backend_v2.payload.request.EmailVerifyRequest;
import com.dsmpear.main.user_backend_v2.payload.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final VerifyUserRepository verifyUserRepository;
    private final VerifyNumberRepository verifyNumberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresentOrElse(
                        user -> verifyUserRepository.findByEmail(request.getEmail())
                                .map(verifyNumber -> userRepository.save(
                                        User.builder()
                                                .name(request.getName())
                                                .email(request.getEmail())
                                                .authStatus(false)
                                                .password(passwordEncoder.encode(request.getPassword()))
                                                .build()
                                )),
                        UserAlreadyExist::new);
    }

    @Override
    public void verify(EmailVerifyRequest request) {
        verifyNumberRepository.findByEmail(request.getEmail())
                .filter(verifyNumber -> verifyNumber.verifyNumber(request.getNumber()))
                .map(verifyNumber -> verifyUserRepository.save(new VerifyUser(request.getEmail())))
                .orElseThrow(NumberNotFoundException::new);
    }
}
