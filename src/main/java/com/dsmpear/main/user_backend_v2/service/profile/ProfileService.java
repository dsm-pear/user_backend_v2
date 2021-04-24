package com.dsmpear.main.user_backend_v2.service.profile;

import com.dsmpear.main.user_backend_v2.payload.response.ProfilePageResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ProfileReportsResponse;
import org.springframework.data.domain.Pageable;

public interface ProfileService {
    ProfilePageResponse getProfile(String email);
    ProfileReportsResponse getReport(String userEmail, Pageable page);
}
