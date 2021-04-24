package com.dsmpear.main.user_backend_v2.entity.reporttype;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportTypeRepository extends CrudRepository<ReportType, Long> {
}
