package com.example.backend.order.controller;

import static com.example.backend.common.enums.UserCookie.*;
import static com.example.backend.order.dto.CartRequest.*;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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
	private final NotificationService notificationService;

	@Operation(summary = "인증 토큰 발급", description = "토큰을 발급한 url을 반환하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "토큰 발급 완료"),
	})
	@GetMapping("/auth/{tableId}/{uuid}")
	public void createAuthToken(@PathVariable("tableId") String tableId, @PathVariable("uuid") String uuid, HttpServletResponse response) throws
		IOException {

		String authLink = orderService.redirectUrl(tableId,uuid);

		response.sendRedirect(authLink);
	}
	@GetMapping("/cart/setUserCookie")
	public ResponseEntity<Void> setUserCookie(HttpServletResponse response){

		// 쿠키 생성 및 설정
		Cookie cookie = new Cookie("JDQR-order-user-id", UUID.randomUUID().toString());
		cookie.setPath("/"); // 쿠키 적용 경로 설정
		// cookie.setHttpOnly(true); // HttpOnly 설정
		cookie.setMaxAge(60 * 20); // 쿠키 유효 시간 : 20분
		response.addCookie(cookie); // 응답에 쿠키 추가

		return ResponseEntity.ok().build();
	}



	@GetMapping("/cart/subscribe")
	public SseEmitter subscribe(HttpServletRequest request){

		String tableId = (String)request.getAttribute("tableId");
		log.warn("tableId : {}",tableId);


		return notificationService.subscribe(tableId);
	}

	@PostMapping("/cart/addItem")
	public void addItemToCart(@RequestBody CartDto productInfo,HttpServletRequest request){

		String tableId = (String)request.getAttribute("tableId");
		log.warn("테이블 id : {}",tableId);

		// 쿠키에서 userId 추출
		String userId = null;
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (USER_COOKIE.getExplain().equals(cookie.getName())) {
					userId = cookie.getValue();
					break;
				}
			}
		}
		log.warn("userId : {}",userId);
		orderService.addItem(tableId,userId,productInfo);
	}

}

