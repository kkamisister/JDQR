package com.example.backend.common.exception;

import java.util.List;

public class ValidationException extends RuntimeException {
	private final List<String> errors;

	public ValidationException(List<String> errors) {
		super("유효성 검증 실패");
		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}
}
