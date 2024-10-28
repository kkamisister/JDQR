package com.example.backend.common.handler;

import static com.example.backend.common.dto.CommonResponse.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.backend.common.dto.CommonResponse;
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(JDQRException.class)
	public ResponseEntity<ResponseWithMessage> handleGlobalException(JDQRException e){
		ErrorCode errorCode = e.getErrorCode();
		ResponseWithMessage responseWithMessage = new ResponseWithMessage(errorCode.getHttpStatus().value(), errorCode.getMessage());

		return new ResponseEntity<>(responseWithMessage, errorCode.getHttpStatus());
	}
}
