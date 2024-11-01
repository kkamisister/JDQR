package com.example.backend.order.dto;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

public record CartResponse() {

	@Schema(name = "장바구니 데이터",description = "현재 장바구니에 담은 데이터")
	public record CartInfo(
		Map<String, Map<Integer,CartDto>> userCartMap,
		String tableName,
		int peopleCnt
	) {
		public static CartInfo of(Map<String, Map<Integer,CartDto>> userCartMap, String tableName, int peopleCnt) {
			return new CartInfo(userCartMap,tableName,peopleCnt);
		}
	}
}
