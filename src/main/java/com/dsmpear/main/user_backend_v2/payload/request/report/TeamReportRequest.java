package com.dsmpear.main.user_backend_v2.payload.request.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TeamReportRequest extends BaseReportRequest {

    @NotBlank
    private String teamName;

    @NotEmpty
    private List<String> members;

}
