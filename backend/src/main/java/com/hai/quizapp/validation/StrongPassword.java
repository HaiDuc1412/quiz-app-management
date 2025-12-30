package com.hai.quizapp.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Custom validation annotation to check for strong password requirements.
 * Password must: - Be at least 8 characters long - Contain at least one
 * uppercase letter - Contain at least one lowercase letter - Contain at least
 * one digit - Contain at least one special character
 * (!@#$%^&*()_+-=[]{}|;:,.<>?)
 */
@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {

    String message() default "Password must contain at least 8 characters, including uppercase, lowercase, digit, and special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
