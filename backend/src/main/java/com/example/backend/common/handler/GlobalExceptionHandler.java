package com.example.backend.common.handler;

import static com.example.backend.common.dto.CommonResponse.*;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.backend.common.dto.CommonResponse;
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(JDQRException.class)
	public ResponseEntity<ResponseWithMessage> handleGlobalException(JDQRException e){
		ErrorCode errorCode = e.getErrorCode();
		ResponseWithMessage responseWithMessage = new ResponseWithMessage(errorCode.getHttpStatus().value(), errorCode.getMessage());

		return new ResponseEntity<>(responseWithMessage, errorCode.getHttpStatus());
	}

	/**
	 * 컨트롤러 단에서 발생하는 유효성 예외를 받는 메서드
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseWithMessage> handleConstraintViolationException(MethodArgumentNotValidException e) {
		// ConstraintViolation에서 각 메시지 추출
		// String errorMessage = e.getConstraintViolations().stream()
		// 	.map(ConstraintViolation::getMessage)  // 메시지만 가져오기 (필드명 제외)
		// 	.collect(Collectors.joining(", "));    // 여러 메시지 연결

		String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

		// HTTP 상태 코드와 커스텀 메시지 생성
		ResponseWithMessage responseWithMessage = new ResponseWithMessage(HttpStatus.BAD_REQUEST.value(), errorMessage);

		// System.out.println("responseWithMessage.status() = " + responseWithMessage.status());
		// System.out.println("responseWithMessage.message() = " + responseWithMessage.message());
		// log.warn("여기!!!");
		return new ResponseEntity<>(responseWithMessage, HttpStatus.BAD_REQUEST);
	}
}
