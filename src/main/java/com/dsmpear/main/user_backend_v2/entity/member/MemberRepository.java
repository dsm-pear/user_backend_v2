package com.dsmpear.main.user_backend_v2.entity.member;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends CrudRepository<Member,Integer> {
    List<Member> findAllByUser(User user);
    Optional<Member> findByReportAndUser(Report report, User user);
    List<Member> findAllByReport(Report report);
}
