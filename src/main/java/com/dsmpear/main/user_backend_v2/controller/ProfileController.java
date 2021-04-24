package com.dsmpear.main.user_backend_v2.controller;

import com.dsmpear.main.user_backend_v2.payload.response.ProfilePageResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ProfileReportsResponse;
import com.dsmpear.main.user_backend_v2.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/profile")
@RestController
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ProfilePageResponse getProfile(@RequestParam("user-email") String userEmail) {
        return profileService.getProfile(userEmail);
    }

    @GetMapping("/report")
    public ProfileReportsResponse getReport(@RequestParam("user-email") String userEmail, Pageable page) {
        return profileService.getReport(userEmail, page);
    }

}
