package com.example.backend.order.controller;

import static com.example.backend.order.dto.CartRequest.*;

import java.io.IOException;

import com.example.backend.common.dto.CommonResponse.*;
import com.example.backend.common.enums.SimpleResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.backend.notification.service.NotificationService;
import com.example.backend.order.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

	@GetMapping("/cart/subscribe")
	public SseEmitter subscribe(HttpServletRequest request){

		String tableId = (String)request.getAttribute("tableId");

		return notificationService.subscribe(tableId);
	}

	@PostMapping("/cart/addItem")
	public void addItemToCart(@RequestBody ProductInfo productInfo,HttpServletRequest request){


		String tableId = (String)request.getAttribute("tableId");
		log.warn("테이블 id : {}",tableId);
		orderService.addItem(tableId,productInfo);
	}

	@PostMapping("/")
	public ResponseWithMessage saveOrder(HttpServletRequest request){
		String tableId = (String)request.getAttribute("tableId");

		SimpleResponseMessage message = orderService.saveWholeOrder(tableId);

		return new ResponseWithMessage(HttpStatus.OK.value(), message.getMessage());
	}

}

