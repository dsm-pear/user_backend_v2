package com.dsmpear.main.user_backend_v2.payload.response;

import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Field;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Grade;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportModifyResponse {

    private String title;

    private String description;

    private LocalDateTime createdAt;

    private List<String> languages;

    private Type type;

    private Grade grade;

    private Access access;

    private Field field;

    private String teamName;

    private List<MemberResponse> member;

    private Long fileId;

    private String github;

}
