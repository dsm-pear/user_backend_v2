package com.dsmpear.main.user_backend_v2.entity.member;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends CrudRepository<Member,Integer> {
    List<Member> findAllByReport(Report report);
}
