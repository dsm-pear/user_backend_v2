package com.dsmpear.main.user_backend_v2.entity.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeRepository extends CrudRepository<Notice,Integer> {
    Page<Notice> findAllByOrderByCreatedAtDesc(Pageable page);
    Optional<Notice> findById(Long id);
}
