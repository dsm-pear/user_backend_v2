package com.dsmpear.main.user_backend_v2.mapper;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.reporttype.ReportType;
import com.dsmpear.main.user_backend_v2.payload.request.ReportRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface ReportTypeMapper {

    @Mapping(target = "report", source = "report")
    @Mapping(target = "reportId", ignore = true)
    ReportType requestToEntity(ReportRequest request, Report report);
}
