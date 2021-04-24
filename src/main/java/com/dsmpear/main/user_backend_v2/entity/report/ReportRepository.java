package com.dsmpear.main.user_backend_v2.entity.report;

import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReportRepository extends CrudRepository<Report,Long> {
    // 보고서 갖고오기
    Optional<Report> findById(Long reportId);

    //제목 검색 ORM
    Page<Report> findAllByReportTypeAccessAndIsAcceptedTrueAndIsSubmittedTrueAndTitleContainsOrderByCreatedAtDesc(Access access, String title, Pageable page);

    List<Report> findAllBy();
}