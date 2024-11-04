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
		int peopleCnt
	) {
		public static CartInfo of(List<CartDto> cartList, String tableName, int peopleCnt) {
			return new CartInfo(cartList,tableName,peopleCnt);
		}
	}
}
