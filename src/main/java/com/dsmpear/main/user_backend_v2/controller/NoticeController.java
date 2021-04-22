package com.dsmpear.main.user_backend_v2.controller;

import com.dsmpear.main.user_backend_v2.payload.response.NoticeContentResponse;
import com.dsmpear.main.user_backend_v2.payload.response.NoticesResponse;
import com.dsmpear.main.user_backend_v2.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/notice")
@RestController
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public NoticesResponse getNoticeList(Pageable page){
        return noticeService.getNoticeList(page);
    }

    @GetMapping("/{noticeId}")
    public NoticeContentResponse getNoticeContent(@PathVariable Long noticeId) {
        return noticeService.getNoticeContent(noticeId);
    }

}
