package com.example.backend.order.controller;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.example.backend.order.dto.CartRequest.*;
import com.example.backend.order.dto.CartResponse.*;
import org.springframework.http.ResponseEntity;
import com.example.backend.common.dto.CommonResponse.*;
import com.example.backend.common.enums.SimpleResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.backend.common.enums.UseCookie;
import com.example.backend.notification.service.NotificationService;
import com.example.backend.order.dto.CartDto;
import com.example.backend.order.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

	private final OrderService orderService;

	@Operation(summary = "인증 토큰 발급", description = "토큰을 발급한 url을 반환하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "토큰 발급 완료"),
	})
	@GetMapping("/auth/{tableId}/{uuid}")
	public void createAuthToken(@PathVariable("tableId") String tableId, @PathVariable("uuid") String uuid, HttpServletResponse response) throws
		IOException {

		String authLink = orderService.redirectUrl(tableId,uuid);

		log.warn("authLink  :{}",authLink);
		response.sendRedirect(authLink);
	}

	@Operation(summary = "쿠키 발급", description = "유저 ID를 랜덤생성하여 반환하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "쿠키 발급 완료"),
	})
	@GetMapping("/cart/cookie")
	public ResponseEntity<Void> setUserCookie(HttpServletResponse response){

		// 쿠키 생성 및 설정
		Cookie cookie = new Cookie(UseCookie.USE_COOKIE.getExplain(), UUID.randomUUID().toString());
		cookie.setPath("/"); // 쿠키 적용 경로 설정
		// cookie.setHttpOnly(true); // HttpOnly 설정
		cookie.setMaxAge(60 * 20); // 쿠키 유효 시간 : 20분
		response.addCookie(cookie); // 응답에 쿠키 추가

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "결제 수행", description = "부분결제를 수행하는 api")
	@PostMapping("/payment")
	public ResponseEntity<ResponseWithData<InitialPaymentResponseDto>> payForOrder(HttpServletRequest request, PaymentRequestDto paymentRequestDto) {
		String tableId = (String)request.getAttribute("tableId");

		InitialPaymentResponseDto initialPaymentResponseDto = orderService.payForOrder(tableId, paymentRequestDto);

		ResponseWithData<InitialPaymentResponseDto> responseWithData = new ResponseWithData<>(HttpStatus.OK.value(), "부분결제 요청에 성공하였습니다", initialPaymentResponseDto);

		return ResponseEntity.status(responseWithData.status())
			.body(responseWithData);
	}

	@Operation(summary = "장바구니 항목 담기", description = "장바구니에 항목을 담는 api")
	@MessageMapping("/cart/add")
	public void addItemToCart(@Payload CartDto productInfo, @Header("simpSessionAttributes") Map<String,Object> attributes){

		String tableId = (String)attributes.get("tableId");
		log.warn("테이블 id : {}",tableId);
		orderService.addItem(tableId,productInfo);
	}


	@Operation(summary = "장바구니 항목 제거", description = "장바구니 항목을 제거하는 api")
	@MessageMapping("/cart/delete")
	public void deleteItem(@Payload CartDto productInfo,@Header("simpSessionAttributes") Map<String,Object> attributes){

		String tableId = (String)attributes.get("tableId");
		log.warn("테이블 id : {}",tableId);
		orderService.deleteItem(tableId,productInfo);

	}


	@PostMapping("/")
	public ResponseEntity<ResponseWithMessage> saveOrder(HttpServletRequest request){
		String tableId = (String)request.getAttribute("tableId");

		SimpleResponseMessage message = orderService.saveWholeOrder(tableId);

		ResponseWithMessage responseWithMessage = new ResponseWithMessage(HttpStatus.OK.value(), message.getMessage());

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

}

