package com.dsmpear.main.user_backend_v2.controller;

import com.dsmpear.main.user_backend_v2.payload.request.QuestionRequest;
import com.dsmpear.main.user_backend_v2.service.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/question")
@RestController
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public void inquiry(@RequestBody @Valid QuestionRequest request) {
        questionService.question(request);
    }

}
