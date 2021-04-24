package com.dsmpear.main.user_backend_v2.controller;

import com.dsmpear.main.user_backend_v2.payload.request.CommentRequest;
import com.dsmpear.main.user_backend_v2.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{reportId}")
    public void writeComment(@RequestBody CommentRequest commentRequest, @PathVariable Long reportId) {
        commentService.createComment(commentRequest, reportId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

    @PatchMapping("/{commentId}")
    public void updateComment(@PathVariable Long commentId, @RequestBody String content) {
        commentService.updateComment(commentId, content);
    }

}
