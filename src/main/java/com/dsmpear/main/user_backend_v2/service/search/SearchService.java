package com.dsmpear.main.user_backend_v2.service.search;

import com.dsmpear.main.user_backend_v2.payload.response.ProfileReportsResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ReportListResponse;
import org.springframework.data.domain.Pageable;

public interface SearchService {
    ReportListResponse searchReport(Pageable pageable, String title);
}
