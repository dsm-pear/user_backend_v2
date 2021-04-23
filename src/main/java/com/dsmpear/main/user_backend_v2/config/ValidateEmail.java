package com.dsmpear.main.user_backend_v2.config;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)     // 이 클래스에 의해 검증됨
@Target(ElementType.FIELD)      // 해당 어노테이션은 필드 위에 정의됨
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateEmail {
    String message() default "email in invalid";
}
