package com.example.backend.order.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.common.exception.ValidationException;
import com.example.backend.order.dto.CartRequest.*;
import com.example.backend.order.dto.CartResponse.*;
import com.example.backend.order.dto.DummyOrderDto;
import com.example.backend.order.dto.OrderResponse.*;
import org.springframework.http.ResponseEntity;
import com.example.backend.common.dto.CommonResponse.*;
import com.example.backend.common.enums.SimpleResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.common.enums.UseCookie;
import com.example.backend.order.dto.CartDto;
import com.example.backend.order.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

	private final OrderService orderService;
	private final SimpMessagingTemplate messagingTemplate;
	private final Validator validator;

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
		cookie.setSecure(true);

		// cookie.setDomain("localhost");
		// SameSite=None 속성을 수동으로 추가하기 위해 Set-Cookie 헤더를 직접 설정
		response.addHeader("Set-Cookie",
			String.format("%s=%s; Max-Age=%d; Path=%s; Secure; SameSite=None",
				cookie.getName(), cookie.getValue(), cookie.getMaxAge(), cookie.getPath()));

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "결제 수행", description = "부분결제를 수행하는 api")
	@MessageMapping("/payment")
	public void finishPayment(
		@Header("tableId") String tableId,  // 헤더에서 직접 tableId 받기
		@Payload SimpleTossPaymentRequestDto simpleTossPaymentRequestDto,  // Payload로 데이터 받기
		@Header("tossOrderId") String tossOrderId,  // 필요에 따라 헤더로 받기
		@Header("status") String status
	) {


		SimpleResponseMessage simpleResponseMessage = orderService.finishPayment(tableId, tossOrderId, status, simpleTossPaymentRequestDto);


		PaymentConfirmResponseDto paymentConfirmResponseDto = PaymentConfirmResponseDto.builder()
			.status(simpleResponseMessage.name())
			.detailMessage(simpleResponseMessage.getMessage())
			.build();
//		String tableId = "6721aa9b0d22a923091eef73";
//		String paymentConfirmResponseDto = "보내졌음?";
		messagingTemplate.convertAndSend("/sub/payment/"+tableId, paymentConfirmResponseDto);
	}

	@Operation(summary = "상위 주문 상태 조회", description = "parentOrder의 상태를 조회하는 api")
	@GetMapping("/status")
	public ResponseEntity<ResponseWithData<ParentOrderInfoResponseDto>> getOrderStatus(HttpServletRequest request) {
		String tableId = (String)request.getAttribute("tableId");

		ParentOrderInfoResponseDto data = orderService.getParentOrderInfo(tableId);

		ResponseWithData<ParentOrderInfoResponseDto> responseWithData = new ResponseWithData<>(HttpStatus.OK.value(), "주문 상태 조회를 완료하였습니다", data);

		return ResponseEntity.status(responseWithData.status()).body(responseWithData);
	}

	@Operation(summary = "주문 상태 변경", description = "parentOrder의 상태를 PENDING -> PAY_WAITING으로 변경하는 api")
	@PostMapping("/status")
	public ResponseEntity<ResponseWithMessage> updateOrderStatus(HttpServletRequest request) {
		String tableId = (String)request.getAttribute("tableId");

		SimpleResponseMessage message = orderService.initPayment(tableId);

		ResponseWithMessage responseWithMessage = new ResponseWithMessage(HttpStatus.OK.value(), message.getMessage());

		return ResponseEntity.status(responseWithMessage.status()).body(responseWithMessage);
	}

	@Operation(summary = "주문 정보 + 결제된 정보", description = "주문한 음식들의 내역과, 결제 현황을 보여 주는 api")
	@GetMapping("/payment")
	public ResponseEntity<ResponseWithData<TotalOrderInfoResponseDto>> getPaymentInfo(HttpServletRequest request) {
		String tableId = (String)request.getAttribute("tableId");

		TotalOrderInfoResponseDto data = orderService.getTotalPaymentInfo(tableId);

		ResponseWithData<TotalOrderInfoResponseDto> responseWithData = new ResponseWithData<>(HttpStatus.OK.value(), "주문 정보와, 결제된 정보", data);

		return ResponseEntity.status(responseWithData.status())
			.body(responseWithData);
	}

	@Operation(summary = "결제 수행", description = "부분결제를 수행하는 api")
	@PostMapping("/payment")
	public ResponseEntity<ResponseWithData<InitialPaymentResponseDto>> payForOrder(HttpServletRequest request, @RequestBody PaymentRequestDto paymentRequestDto) {
		String tableId = (String)request.getAttribute("tableId");

		InitialPaymentResponseDto initialPaymentResponseDto = orderService.payForOrder(tableId, paymentRequestDto);

		ResponseWithData<InitialPaymentResponseDto> responseWithData = new ResponseWithData<>(HttpStatus.OK.value(), "부분결제 요청에 성공하였습니다", initialPaymentResponseDto);

		return ResponseEntity.status(responseWithData.status())
			.body(responseWithData);
	}

	@Operation(summary = "장바구니 항목 담기", description = "장바구니에 항목을 담는 api")
	@MessageMapping("/cart/add")
	public void addItemToCart(@Payload CartDto productInfo, @Header("simpSessionAttributes") Map<String,Object> attributes){

		// 수동 검증 수행
		Set<ConstraintViolation<CartDto>> violations = validator.validate(productInfo);
		if (!violations.isEmpty()) {
			// 유효성 검증 실패 예외 발생
			List<String> errorMessages = violations.stream()
				.map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
				.collect(Collectors.toList());

			throw new ValidationException(errorMessages); // 유효성 검증 실패 예외 발생
		}

		String tableId = (String)attributes.get("tableId");
		log.warn("테이블 id : {}",tableId);
		orderService.addItem(tableId, productInfo);
	}

//	// todo: order data 삽입용 api. 데이터 삽입 이후 삭제
//	@PostMapping("/dummy")
//	public void addDummyOrderData(@RequestBody DummyOrderDto productInfo){
//		orderService.addDummyOrderData(productInfo);
//	}


	@Operation(summary = "장바구니 항목 제거", description = "장바구니 항목을 제거하는 api")
	@MessageMapping("/cart/delete")
	public void deleteItem(@Payload CartDto productInfo,@Header("simpSessionAttributes") Map<String,Object> attributes){

		String tableId = (String)attributes.get("tableId");
		log.warn("테이블 id : {}",tableId);
		orderService.deleteItem(tableId,productInfo);

	}


	@PostMapping("")
	public ResponseEntity<ResponseWithMessage> saveOrder(HttpServletRequest request){
		String tableId = (String)request.getAttribute("tableId");

		SimpleResponseMessage message = orderService.saveWholeOrder(tableId);

		ResponseWithMessage responseWithMessage = new ResponseWithMessage(HttpStatus.OK.value(), message.getMessage());

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

	@GetMapping("")
	public ResponseEntity<ResponseWithData<TotalOrderInfoResponseDto>> getOrderInfo(HttpServletRequest request) {
		String tableId = (String)request.getAttribute("tableId");

		TotalOrderInfoResponseDto responseDto = orderService.getOrderInfo(tableId);

		ResponseWithData<TotalOrderInfoResponseDto> responseWithData = new ResponseWithData<>(HttpStatus.OK.value(), "주문 조회를 완료하였습니다", responseDto);
		return ResponseEntity.status(responseWithData.status())
			.body(responseWithData);
	}
}

