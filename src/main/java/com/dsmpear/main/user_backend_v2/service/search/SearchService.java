package com.dsmpear.main.user_backend_v2.service.search;

import com.dsmpear.main.user_backend_v2.payload.response.ReportListResponse;
import com.dsmpear.main.user_backend_v2.payload.response.SearchProfileResponse;
import org.springframework.data.domain.Pageable;

public interface SearchService {
    SearchProfileResponse searchProfile(String keyword, Pageable page);
    ReportListResponse searchReport(Pageable pageable, String title);
}
