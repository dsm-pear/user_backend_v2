package com.dsmpear.main.user_backend_v2.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String description;

}
