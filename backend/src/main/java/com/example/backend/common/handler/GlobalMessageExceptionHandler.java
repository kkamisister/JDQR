package com.example.backend.common.handler;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.backend.common.dto.CommonResponse.ResponseWithMessage;
import com.example.backend.common.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalMessageExceptionHandler {

	private final GlobalExceptionHandler globalExceptionHandler;
	private final SimpMessagingTemplate template;
	private final MethodProvider methodProvider;

	public GlobalMessageExceptionHandler(GlobalExceptionHandler globalExceptionHandler, SimpMessagingTemplate template) {
		this.globalExceptionHandler = globalExceptionHandler;
		this.template = template;
		this.methodProvider = new MethodProvider();
	}

	// GlobalExceptionHandler에서 처리하는 모든 타입의 예외를 그대로 메시지 예외 핸들러에서도 처리해야하기에 래퍼 구조를 사용
	@MessageExceptionHandler(ValidationException.class)
	@SendToUser(destinations = "/queue/errors", broadcast = false)
	public ResponseWithMessage handleMethodArgumentNotValidException(ValidationException e) {
		log.warn("유효성 검사 실패: {}", e.getMessage());

		// 유효성 검사 오류 메시지 생성
		StringBuilder sb = new StringBuilder();
		for(String s : e.getErrors()){
			sb.append(s).append(" ");
		}
		log.warn("error : {}",sb);

		return new ResponseWithMessage(HttpStatus.BAD_REQUEST.value(), sb.toString());
	}

	private class MethodProvider{
		public ResponseEntity<ResponseWithMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
			return globalExceptionHandler.handleConstraintViolationException(e);
		}
	}
}
