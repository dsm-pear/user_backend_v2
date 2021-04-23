package com.dsmpear.main.user_backend_v2.controller;

import com.dsmpear.main.user_backend_v2.payload.response.ReportListResponse;
import com.dsmpear.main.user_backend_v2.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/search")
@RequiredArgsConstructor
@RestController
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/report")
    public ReportListResponse reportListResponse(@RequestParam("keyword") String keyword,
                                                 Pageable pageable) {
        return searchService.searchReport(pageable, keyword);
    }

}
