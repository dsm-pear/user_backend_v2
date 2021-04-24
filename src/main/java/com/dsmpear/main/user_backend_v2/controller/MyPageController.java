package com.dsmpear.main.user_backend_v2.controller;

import com.dsmpear.main.user_backend_v2.payload.request.SetSelfIntroRequest;
import com.dsmpear.main.user_backend_v2.payload.response.ProfilePageResponse;
import com.dsmpear.main.user_backend_v2.service.mypage.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/user/profile")
@RestController
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping
    public ProfilePageResponse getMyPage(){
        return myPageService.getMyPage();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setSelfIntro(@RequestBody SetSelfIntroRequest request) {
        myPageService.setSelfIntro(request.getIntro(), request.getGithub());
    }

    /*@GetMapping("/report")
    public ProfileReportListResponse getReport(Pageable page){
        return myPageReportService.getReport(page);
    }
*/
}
