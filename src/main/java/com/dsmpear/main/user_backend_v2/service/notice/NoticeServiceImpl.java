package com.dsmpear.main.user_backend_v2.service.notice;

import com.dsmpear.main.user_backend_v2.entity.notice.Notice;
import com.dsmpear.main.user_backend_v2.entity.notice.NoticeRepository;
import com.dsmpear.main.user_backend_v2.exception.NoticeNotFoundException;
import com.dsmpear.main.user_backend_v2.payload.response.NoticeContentResponse;
import com.dsmpear.main.user_backend_v2.payload.response.NoticeResponse;
import com.dsmpear.main.user_backend_v2.payload.response.NoticesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    public NoticesResponse getNoticeList(Pageable page) {
        Page<Notice> noticePage = noticeRepository.findAllByOrderByCreatedAtDesc(page);
        List<NoticeResponse> notices = new ArrayList<>();

        for(Notice notice : noticePage) {
            notices.add(
                    NoticeResponse.builder()
                            .id(notice.getId())
                            .title(notice.getTitle())
                            .createdAt(notice.getCreatedAt())
                            .build()
            );
        }

        return NoticesResponse.builder()
                .totalElements(noticePage.getTotalElements())
                .totalPages(noticePage.getTotalPages())
                .noticeResponses(notices)
                .build();
    }

    @Override
    public NoticeContentResponse getNoticeContent(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(NoticeNotFoundException::new);

        return NoticeContentResponse.builder()
                .title(notice.getTitle())
                .description(notice.getDescription())
                .createdAt(notice.getCreatedAt())
                .build();
    }

}
