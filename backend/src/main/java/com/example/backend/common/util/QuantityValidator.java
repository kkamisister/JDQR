package com.example.backend.common.util;

import com.example.backend.common.annotation.CustomPositive;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuantityValidator implements ConstraintValidator<CustomPositive,Integer> {

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
		return value != null && (value == -1 || value > 0);
	}
}
