package com.dsmpear.main.user_backend_v2.service.comment;

import com.dsmpear.main.user_backend_v2.entity.comment.Comment;
import com.dsmpear.main.user_backend_v2.entity.comment.CommentRepository;
import com.dsmpear.main.user_backend_v2.exception.CommentNotFoundException;
import com.dsmpear.main.user_backend_v2.exception.InvalidAccessException;
import com.dsmpear.main.user_backend_v2.facade.report.ReportFacade;
import com.dsmpear.main.user_backend_v2.facade.user.UserFacade;
import com.dsmpear.main.user_backend_v2.mapper.CommentMapper;
import com.dsmpear.main.user_backend_v2.payload.request.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserFacade userFacade;
    private final CommentMapper commentMapper;
    private final ReportFacade reportFacade;

    @Override
    public void createComment(CommentRequest request, Long reportId) {
        Comment comment = commentMapper.requestToEntity(request, reportFacade.createAccessReport(reportId), userFacade.createAuthUser());
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void updateComment(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        validateAccess(comment);
        comment.updateContent(content);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        validateAccess(comment);
        commentRepository.delete(comment);
    }

    private void validateAccess(Comment comment) {
        if(!comment.getUser().equals(userFacade.createAuthUser()))
            throw new InvalidAccessException();
    }
}
