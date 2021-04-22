package com.dsmpear.main.user_backend_v2.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class NoticeListResponse extends PageResponse {

    private List<NoticeResponse> noticeResponses;

    @Builder
    public NoticeListResponse(Long totalElements, int totalPages, List<NoticeResponse> noticeResponses) {
        super(totalElements, totalPages);
        this.noticeResponses = noticeResponses;
    }

}
