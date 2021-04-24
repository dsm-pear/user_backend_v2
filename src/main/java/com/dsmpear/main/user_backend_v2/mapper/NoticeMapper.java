package com.dsmpear.main.user_backend_v2.mapper;

import com.dsmpear.main.user_backend_v2.entity.notice.Notice;
import com.dsmpear.main.user_backend_v2.payload.response.NoticeContentResponse;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface NoticeMapper {

    NoticeContentResponse entityToResponse(Notice notice);
}
