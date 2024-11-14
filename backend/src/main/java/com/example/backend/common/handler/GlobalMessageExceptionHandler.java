// package com.example.backend.common.handler;
//
// import java.security.Principal;
//
// import org.springframework.http.ResponseEntity;
// import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
// import org.springframework.messaging.handler.annotation.Payload;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.web.bind.MethodArgumentNotValidException;
// import org.springframework.web.bind.annotation.ControllerAdvice;
//
// import com.example.backend.common.dto.CommonResponse.ResponseWithMessage;
// import com.example.backend.common.dto.SocketRequest;
//
// import lombok.extern.slf4j.Slf4j;
//
// @ControllerAdvice
// @Slf4j
// public class GlobalMessageExceptionHandler {
//
// 	private final GlobalExceptionHandler globalExceptionHandler;
// 	private final SimpMessagingTemplate template;
// 	private final MethodProvider methodProvider;
//
// 	public GlobalMessageExceptionHandler(GlobalExceptionHandler globalExceptionHandler, SimpMessagingTemplate template) {
// 		this.globalExceptionHandler = globalExceptionHandler;
// 		this.template = template;
// 		this.methodProvider = new MethodProvider();
// 	}
//
// 	// GlobalExceptionHandler에서 처리하는 모든 타입의 예외를 그대로 메시지 예외 핸들러에서도 처리해야하기에 래퍼 구조를 사용
// 	@MessageExceptionHandler(MethodArgumentNotValidException.class)
// 	public void handleMethodArgumentNotValidException(Principal principal, @Payload SocketRequest request, MethodArgumentNotValidException e) {
// 		log.warn("여기옴????");
// 		template.convertAndSendToUser(principal.getName(), request.getResponseChannel(), methodProvider.handleMethodArgumentNotValidException(e).getBody());
// 	}
//
//
//
// 	private class MethodProvider{
// 		public ResponseEntity<ResponseWithMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
// 			return globalExceptionHandler.handleConstraintViolationException(e);
// 		}
// 	}
// }
