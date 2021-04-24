package com.dsmpear.main.user_backend_v2.entity.comment;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAllByReport(Report report);
}
