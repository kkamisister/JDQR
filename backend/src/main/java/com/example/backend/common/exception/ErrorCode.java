package com.example.backend.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// 유저관련 에러
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다"),

	// 옵션관련 에러
	OPTIONGROUP_NOT_FOUND(HttpStatus.BAD_REQUEST,"옵션그룹이 존재하지 않습니다"),

	// 태그관련 에러
	TAG_NOT_FOUND(HttpStatus.BAD_REQUEST, "태그가 존재하지 않습니다"),

	// 토큰 관련 에러
	TOKEN_REISSUE_FAIL(HttpStatus.BAD_REQUEST,"토큰 재발급에 문제가 발생했습니다"),

	// 외부 api 호출시 400번대 에러 발생
	WEBCLIENT_400_ERROR(HttpStatus.BAD_REQUEST, "외부 api 호출 도중 400번대 에러가 발생하였습니다"),
	// 외부 api 호출시 500번대 에러 발생
	WEBCLIENT_500_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 api 호출 도중 500번대 에러가 발생하였습니다"),

	// 테스트용 에러
	FUCKED_UP_QR(HttpStatus.BAD_REQUEST,"되었습니다");

	private final HttpStatus httpStatus;
	private final String message;

}
