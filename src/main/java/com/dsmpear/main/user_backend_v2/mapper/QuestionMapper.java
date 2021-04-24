package com.dsmpear.main.user_backend_v2.mapper;

import com.dsmpear.main.user_backend_v2.entity.question.Question;
import com.dsmpear.main.user_backend_v2.payload.request.QuestionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface QuestionMapper {

    @Mapping(target = "id", ignore = true)
    Question requestToEntity(QuestionRequest request);
}
