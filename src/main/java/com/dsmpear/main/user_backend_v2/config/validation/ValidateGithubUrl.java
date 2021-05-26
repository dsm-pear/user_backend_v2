package com.dsmpear.main.user_backend_v2.config.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GithubUrlValidator.class)     // 이 클래스에 의해 검증됨
@Target({ElementType.FIELD, ElementType.PARAMETER})      // 해당 어노테이션은 필드 위에 정의됨
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateGithubUrl {
    String message() default "email in invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
