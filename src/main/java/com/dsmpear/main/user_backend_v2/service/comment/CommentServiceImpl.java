package com.dsmpear.main.user_backend_v2.service.comment;

import com.dsmpear.main.user_backend_v2.entity.comment.Comment;
import com.dsmpear.main.user_backend_v2.entity.comment.CommentRepository;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.exception.CommentNotFoundException;
import com.dsmpear.main.user_backend_v2.exception.InvalidAccessException;
import com.dsmpear.main.user_backend_v2.factory.ReportFactory;
import com.dsmpear.main.user_backend_v2.factory.UserFactory;
import com.dsmpear.main.user_backend_v2.mapper.CommentMapper;
import com.dsmpear.main.user_backend_v2.payload.request.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserFactory userFactory;
    private final CommentMapper commentMapper;
    private final ReportFactory reportFactory;

    @Override
    public void createComment(CommentRequest request, Long reportId) {
        commentRepository.save(
                commentMapper.requestToEntity(request, reportFactory.create(reportId.toString()), userFactory.createAuthUser())
        );
    }

    @Override
    @Transactional
    public Long updateComment(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        validateAccess(comment);
        comment.updateContent(content);
        return comment.getId();
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        validateAccess(comment);
        commentRepository.delete(comment);
    }

    private void validateAccess(Comment comment) {
        if(comment.getUser().equals(userFactory.createAuthUser()))
            throw new InvalidAccessException();
    }
}
