package com.dsmpear.main.user_backend_v2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
@Component
public class EmailValidator implements ConstraintValidator<ValidateEmail, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email.endsWith("@dsm.hs.kr");
    }

}
