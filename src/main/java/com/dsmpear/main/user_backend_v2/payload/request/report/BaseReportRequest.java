package com.dsmpear.main.user_backend_v2.payload.request.report;

import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Field;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Grade;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    protected String title;

    @NotBlank
    protected String description;

    protected List<String> languages;

    @NotNull
    protected Type type;

    @NotNull
    protected Access access;

    @NotNull
    protected Field field;

    @NotNull
    protected Grade grade;

    @NotNull
    protected Boolean isSubmitted;

    protected String github;

}
