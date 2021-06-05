package com.dsmpear.main.user_backend_v2.mapper;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.payload.request.report.BaseReportRequest;
import com.dsmpear.main.user_backend_v2.payload.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface ReportMapper {

    @Mapping(target = "reportType.type", source = "request.type")
    @Mapping(target = "reportType.field", source = "request.field")
    @Mapping(target = "reportType.access", source = "request.access")
    @Mapping(target = "reportType.grade", source = "request.grade")
    @Mapping(target = "languages", source = "request.languages")
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "status.isSubmitted", source = "request.isSubmitted")
    @Mapping(target = "status.isAccepted", defaultValue = "false", ignore = true)
    Report requestToEntity(BaseReportRequest request, User user);

    @Mapping(source = "report.reportType.type", target = "type")
    @Mapping(source = "report.id", target = "reportId")
    ReportResponse entityToResponse(Report report);

    @Mapping(source = "report.languages", target = "languages")
    @Mapping(source = "report.reportType.type", target = "type")
    @Mapping(source = "report.reportType.field", target = "field")
    @Mapping(source = "report.reportType.access", target = "access")
    @Mapping(source = "report.reportType.grade", target = "grade")
    @Mapping(source = "report.reportFile.id", target = "fileId")
    @Mapping(source = "comments", target = "comments")
    @Mapping(source = "member", target = "member")
    @Mapping(source = "report.createdAt", target = "createdAt")
    @Mapping(source = "report.status.isSubmitted", target = "isSubmitted")
    ReportContentResponse entityToContentResponse(Report report, Boolean isMine,
                                                  List<ReportCommentsResponse> comments, List<MemberResponse> member);

    @Mapping(source = "report.languages", target = "languages")
    @Mapping(source = "report.reportType.type", target = "type")
    @Mapping(source = "report.reportType.field", target = "field")
    @Mapping(source = "report.reportType.access", target = "access")
    @Mapping(source = "report.reportType.grade", target = "grade")
    @Mapping(source = "report.reportFile.id", target = "fileId")
    @Mapping(source = "member", target = "member")
    @Mapping(source = "report.createdAt", target = "createdAt")
    ReportModifyResponse entityToModifyResponse(Report report, Boolean isMine, List<MemberResponse> member);

}
