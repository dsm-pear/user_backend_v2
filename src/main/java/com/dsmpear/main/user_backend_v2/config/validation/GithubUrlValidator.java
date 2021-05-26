package com.dsmpear.main.user_backend_v2.config.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
@Component
public class GithubUrlValidator implements ConstraintValidator<ValidateGithubUrl, String> {

    private static final String GITHUB_LINK = "github.com";

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        return url == null || isStartsWithGithub(url);
    }

    private boolean isStartsWithGithub(String url) {
        return url.contains(GITHUB_LINK);
    }
}
