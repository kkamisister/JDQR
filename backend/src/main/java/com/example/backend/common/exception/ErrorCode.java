package com.example.backend.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// 유저관련 에러
	USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "사용자가 존재하지 않습니다"),
	// 메뉴 관련 에러
	DISH_NOT_FOUND(HttpStatus.BAD_REQUEST, "메뉴가 존재하지 않습니다"),

	// 카테고리 관련 에러
	DUPLICATED_CATEGORY(HttpStatus.OK, "중복된 카테고리 입니다"),
	OCCUPIED_CATEGORY(HttpStatus.OK, "다른 메뉴에 포함된 카테고리 입니다"),

	// 식당 관련 에러
	RESTAURANT_NOT_FOUND(HttpStatus.BAD_REQUEST,"사업장이 존재하지 않습니다"),

	// 테이블관련 에러
	TABLE_NOT_FOUND(HttpStatus.BAD_REQUEST,"테이블이 존재하지 않습니다"),

	// 결제관련 에러
	PARENT_ORDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "상위 주문이 존재하지 않습니다"),
	ORDER_ALREADY_PAID(HttpStatus.BAD_REQUEST, "이미 결제된 주문입니다"),
	PAYMENT_METHOD_NOT_VALID(HttpStatus.BAD_REQUEST, "잘못된 결제 방식으로 결제를 시도하였습니다"),
	EXCEED_TOTAL_PURCHASE_AMOUNT(HttpStatus.BAD_REQUEST, "결제 금액이 총 주문 금액을 초과하였습니다"),
	EXCEED_MENU_PURCHASE_AMOUNT(HttpStatus.BAD_REQUEST, "이미 결제된 메뉴에 대해 결제를 시도하였습니다"),
	TOSS_CONFIRM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "토스 확정 api 호출 중 에러가 발생하였습니다"),

	// 옵션관련 에러
	OPTIONGROUP_NOT_FOUND(HttpStatus.BAD_REQUEST,"옵션그룹이 존재하지 않습니다"),

	// 태그관련 에러
	TAG_NOT_FOUND(HttpStatus.BAD_REQUEST, "태그가 존재하지 않습니다"),

	// 토큰 관련 에러
	TOKEN_REISSUE_FAIL(HttpStatus.BAD_REQUEST,"토큰 재발급에 문제가 발생했습니다"),
	TOKEN_IS_NOT_VALID(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다"),
	TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST, "토큰이 만료되었습니다"),

	// validation 관련 에러
	// 내부 로직이 잘못된 경우
	VALIDATION_ERROR_INTERNAL(HttpStatus.INTERNAL_SERVER_ERROR, "내부 로직 수행 중 validation 관련 에러가 발생하였습니다"),

	// 외부 api 호출시 400번대 에러 발생
	WEBCLIENT_400_ERROR(HttpStatus.BAD_REQUEST, "외부 api 호출 도중 400번대 에러가 발생하였습니다"),
	// 외부 api 호출시 500번대 에러 발생
	WEBCLIENT_500_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 api 호출 도중 500번대 에러가 발생하였습니다"),

	// S3 관련 에러
	S3_IMAGE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3 업로드 도중 에러가 발생하였습니다"),

	// 테스트용 에러
	FUCKED_UP_QR(HttpStatus.BAD_REQUEST,"되었습니다");

	private final HttpStatus httpStatus;
	private final String message;
}
