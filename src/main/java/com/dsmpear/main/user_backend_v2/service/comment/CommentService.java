package com.dsmpear.main.user_backend_v2.service.comment;

import com.dsmpear.main.user_backend_v2.payload.request.CommentRequest;

public interface CommentService {
    void createComment(CommentRequest request, Long reportId);
    void updateComment(Long commentId, String content);
    void deleteComment(Long commentId);
}
