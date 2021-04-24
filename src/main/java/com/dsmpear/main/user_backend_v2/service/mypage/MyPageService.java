package com.dsmpear.main.user_backend_v2.service.mypage;

import com.dsmpear.main.user_backend_v2.payload.response.ProfilePageResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ProfileReportsResponse;
import org.springframework.data.domain.Pageable;

public interface MyPageService {
    ProfilePageResponse getMyPage();
    void setSelfIntro(String intro, String gitHub);
    ProfileReportsResponse getReport(Pageable page);
}
