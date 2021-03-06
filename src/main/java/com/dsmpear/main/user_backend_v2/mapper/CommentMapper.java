package com.dsmpear.main.user_backend_v2.mapper;

import com.dsmpear.main.user_backend_v2.entity.comment.Comment;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.payload.request.CommentRequest;
import com.dsmpear.main.user_backend_v2.payload.response.ReportCommentsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface CommentMapper {

    @Mapping(source = "report", target = "report")
    @Mapping(target = "super.id", ignore = true)
    Comment requestToEntity(CommentRequest commentRequest, Report report, User user);

    @Mapping(source = "comment.id", target = "commentId")
    @Mapping(source = "comment.user.email", target = "userEmail")
    @Mapping(source = "comment.user.name", target = "userName")
    ReportCommentsResponse entityToResponse(Comment comment, Boolean isMine);

}
