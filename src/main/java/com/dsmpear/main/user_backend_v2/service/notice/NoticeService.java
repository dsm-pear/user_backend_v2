package com.dsmpear.main.user_backend_v2.service.notice;

import com.dsmpear.main.user_backend_v2.payload.response.NoticeContentResponse;
import com.dsmpear.main.user_backend_v2.payload.response.NoticesResponse;
import org.springframework.data.domain.Pageable;

public interface NoticeService {
    NoticesResponse getNoticeList(Pageable page);
    NoticeContentResponse getNoticeContent(Integer noticeId);
}
