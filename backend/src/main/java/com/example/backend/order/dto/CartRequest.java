package com.example.backend.order.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record CartRequest() {

	@Schema(name = "음식 데이터",description = "장바구니에 담은 음식 데이터")
	public record ProductInfo(
		Integer id,
		String name,
		String category,
		List<ProductOption> options,
		int price,
		int count
 	){

	}



}
