package com.dsmpear.main.user_backend_v2.service.mypage;

import com.dsmpear.main.user_backend_v2.payload.response.ProfilePageResponse;

public interface MyPageService {
    ProfilePageResponse getMyPage();
    void setSelfIntro(String intro, String gitHub);
}
