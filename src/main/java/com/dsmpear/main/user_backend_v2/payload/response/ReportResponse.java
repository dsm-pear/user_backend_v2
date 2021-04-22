package com.dsmpear.main.user_backend_v2.payload.response;

import com.dsmpear.main.user_backend_v2.entity.report.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {

    private Integer reportId;

    private LocalDateTime createdAt;

    private String title;

    private Type type;

}

