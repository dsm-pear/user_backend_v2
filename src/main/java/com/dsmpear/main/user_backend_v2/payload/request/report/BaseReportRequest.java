package com.dsmpear.main.user_backend_v2.payload.request.report;

import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Field;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Grade;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseReportRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private List<String> languages;

    @NotNull
    private Type type;

    @NotNull
    private Access access;

    @NotNull
    private Field field;

    @NotNull
    private Grade grade;

    @NotNull
    private Boolean isSubmitted;

    private String github;

}
