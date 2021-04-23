package com.dsmpear.main.user_backend_v2.service.comment;

import com.dsmpear.main.user_backend_v2.payload.request.CommentRequest;

public interface Comment {
    void createComment(CommentRequest request, Long reportId);
    Integer updateComment(Integer commentId, String content);
    void deleteComment(Integer commentId);
}
