package com.dsmpear.main.user_backend_v2.service.profile;

import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.exception.UserNotFoundException;
import com.dsmpear.main.user_backend_v2.payload.response.ProfilePageResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ProfileReportsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

    @Override
    public ProfilePageResponse getProfile(String email) {
        User user = userRepository.findById(email)
                .orElseThrow(UserNotFoundException::new);

        return ProfilePageResponse.builder()
                .userName(user.getName())
                .userEmail(user.getEmail())
                .gitHub(user.getGitHub())
                .selfIntro(user.getSelfIntro())
                .build();
    }

    @Override
    public ProfileReportsResponse getReport(String userEmail, Pageable page) {
        return null;
    }

}
