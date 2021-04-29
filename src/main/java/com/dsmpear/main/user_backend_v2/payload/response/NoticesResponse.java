package com.dsmpear.main.user_backend_v2.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class NoticesResponse extends PageResponse {

    private List<NoticeResponse> noticeResponses;

}
