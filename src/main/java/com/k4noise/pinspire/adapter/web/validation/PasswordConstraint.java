package com.k4noise.pinspire.adapter.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.lang.annotation.*;

@Constraint(validatedBy = {})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain one uppercase letter, one lowercase letter and one digit")
public @interface PasswordConstraint {
    String message() default "Wrong password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}