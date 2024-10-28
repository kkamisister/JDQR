package com.example.backend.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// 유저관련 에러
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다"),

	// 테스트용 에러
	FUCKED_UP_QR(HttpStatus.BAD_REQUEST,"되었습니다");

	private final HttpStatus httpStatus;
	private final String message;

}
