package com.example.backend.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.messaging.handler.annotation.Payload;

import com.example.backend.common.util.QuantityValidator;

import jakarta.validation.Constraint;

@Constraint(validatedBy = QuantityValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomPositive {
	String message() default "수량은 -1이거나 양수여야 합니다";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
