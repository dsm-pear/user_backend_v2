package com.dsmpear.main.user_backend_v2.service.question;

import com.dsmpear.main.user_backend_v2.entity.question.Question;
import com.dsmpear.main.user_backend_v2.entity.question.QuestionRepository;
import com.dsmpear.main.user_backend_v2.payload.request.QuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public void question(QuestionRequest request) {
        questionRepository.save(
                Question.builder()
                        .email(request.getEmail())
                        .description(request.getDescription())
                        .build()
        );
    }

}
