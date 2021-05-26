package com.dsmpear.main.user_backend_v2.payload.request;

import com.dsmpear.main.user_backend_v2.config.validation.ValidateEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NotNull
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    @ValidateEmail
    private String email;

}
