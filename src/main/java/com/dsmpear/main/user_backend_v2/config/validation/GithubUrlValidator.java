package com.dsmpear.main.user_backend_v2.config.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
@Component
public class GithubUrlValidator implements ConstraintValidator<ValidateGithubUrl, String> {

    private static final String HTTP_GITHUB_LINK = "http://github.com";
    private static final String HTTPS_GITHUB_LINK = "https://github.com";

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        return url == null || isStartsWithGithub(url);
    }

    private boolean isStartsWithGithub(String url) {
        return url.startsWith(HTTP_GITHUB_LINK)
                || url.startsWith(HTTPS_GITHUB_LINK);
    }
}
