package com.dsmpear.main.user_backend_v2.service.mypage;

import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.exception.UserNotFoundException;
import com.dsmpear.main.user_backend_v2.payload.response.ProfilePageResponse;
import com.dsmpear.main.user_backend_v2.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyPageServiceImpl implements MyPageService {

    private final UserRepository userRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public ProfilePageResponse getMyPage() {
        return null;
    }

    @Override
    public void setSelfIntro(String intro, String gitHub) {
        User user = userRepository.findById(authenticationFacade.getEmail())
                .orElseThrow(UserNotFoundException::new);

        user.updateInfo(intro, gitHub);

        userRepository.save(user);
    }

}
