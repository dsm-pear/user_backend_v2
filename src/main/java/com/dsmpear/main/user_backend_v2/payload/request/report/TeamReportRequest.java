package com.dsmpear.main.user_backend_v2.payload.request.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TeamReportRequest extends BaseReportRequest {

    private String teamName;

    private List<String> members;

}
