package com.dsmpear.main.user_backend_v2.service.search;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.repository.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.mapper.ReportMapper;
import com.dsmpear.main.user_backend_v2.payload.response.ReportListResponse;
import com.dsmpear.main.user_backend_v2.payload.response.SearchProfileResponse;
import com.dsmpear.main.user_backend_v2.payload.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final UserRepository userRepository;

    @Override
    public SearchProfileResponse searchProfile(String keyword, Pageable page) {
        Page<User> users = userRepository.findAllByNameContainingOrderByName(keyword, page);
        List<UserResponse> userResponses = new ArrayList<>();

        for(User user : users) {
            userResponses.add(
                    UserResponse.builder()
                            .name(user.getName())
                            .email(user.getEmail())
                            .build()
            );
        }

        return SearchProfileResponse.builder()
                .totalElements(users.getTotalElements())
                .totalPages(users.getTotalPages())
                .userResponses(userResponses)
                .build();
    }

    @Override
    public ReportListResponse searchReport(Pageable pageable, String title) {
        Page<Report> reportPage = reportRepository.findAllByReportTypeAccessAndStatusIsAcceptedTrueAndStatusIsSubmittedTrueAndTitleContainsOrderByCreatedAtDesc(Access.EVERY, title, pageable);

        return ReportListResponse.builder()
                .totalElements(reportPage.getTotalElements())
                .totalPages(reportPage.getTotalPages())
                .reportResponses(reportPage
                        .map(reportMapper::entityToResponse)
                        .stream().collect(Collectors.toList()))
                .build();
    }

}
