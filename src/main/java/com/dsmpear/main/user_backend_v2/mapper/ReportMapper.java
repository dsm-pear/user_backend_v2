package com.dsmpear.main.user_backend_v2.mapper;

import com.dsmpear.main.user_backend_v2.entity.language.Language;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.reporttype.ReportType;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.payload.request.ReportRequest;
import com.dsmpear.main.user_backend_v2.payload.response.MemberResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ReportCommentsResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ReportContentResponse;
import com.dsmpear.main.user_backend_v2.payload.response.ReportResponse;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import javax.persistence.MapsId;
import java.util.List;

@Mapper(config = MapStructConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comment", ignore = true)
    @Mapping(target = "isAccepted", defaultValue = "false", ignore = true)
    @Mapping(target = "reportType.type", source = "request.type")
    @Mapping(target = "reportType.field", source = "request.field")
    @Mapping(target = "reportType.access", source = "request.access")
    @Mapping(target = "reportType.grade", source = "request.grade")
    @Mapping(target = "languages", ignore = true)
    Report requestToEntity(ReportRequest request, User user);

    @Mapping(source = "report.reportType.type", target = "type")
    ReportResponse entityToResponse(Report report);

    @Mapping(source = "report.reportType.type", target = "type")
    @Mapping(source = "report.reportType.field", target = "field")
    @Mapping(source = "report.reportType.access", target = "access")
    @Mapping(source = "report.reportType.grade", target = "grade")
    @Mapping(source = "report.reportFile.fileName", target = "fileName")
    @Mapping(source = "report.reportFile.id", target = "fileId")
    @Mapping(source = "languages", target = "languages")
    @Mapping(source = "comments", target = "comments")
    @Mapping(source = "member", target = "member")
    @Mapping(source = "report.createdAt", target = "createdAt")
    ReportContentResponse entityToContentResponse(Report report, Boolean isMine,List<String> languages,
                                                  List<ReportCommentsResponse> comments, List<MemberResponse> member);
}
