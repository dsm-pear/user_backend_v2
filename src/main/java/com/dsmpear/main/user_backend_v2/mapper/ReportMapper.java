package com.dsmpear.main.user_backend_v2.mapper;

import com.dsmpear.main.user_backend_v2.entity.language.Language;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.payload.request.ReportRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

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
}
