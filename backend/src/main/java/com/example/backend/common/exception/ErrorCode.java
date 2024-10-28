package com.example.backend.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// 토큰 관련 에러
	TOKEN_REISSUE_FAIL(HttpStatus.BAD_REQUEST,"토큰 재발급에 문제가 발생했습니다"),

	// 테스트용 에러
	FUCKED_UP_QR(HttpStatus.BAD_REQUEST,"되었습니다");

	private final HttpStatus httpStatus;
	private final String message;

}
