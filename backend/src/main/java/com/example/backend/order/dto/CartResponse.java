package com.example.backend.order.dto;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record CartResponse() {

	@Schema(name = "장바구니 데이터",description = "현재 장바구니에 담은 데이터")
	public record CartInfo(
		List<CartDto> cartList,
		String tableName,
		int peopleCnt,
		int totalPrice,
		int totalQuantity
	) {
		public static CartInfo of(List<CartDto> cartList, String tableName, int peopleCnt,int totalPrice,int totalQuantity) {
			return new CartInfo(cartList,tableName,peopleCnt,totalPrice,totalQuantity);
		}
	}

	@Schema(name = "토스 결제 요청 데이터", description = "서버에서 저장한 주문 번호와 수량을 담는 response dto")
	@Builder
	public record InitialPaymentResponseDto(
		String tossOrderId,
		Integer amount
	) {

	}
}
