package com.example.backend.order.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "음식 데이터",description = "장바구니에 담은 음식 데이터")
public record CartDto(

	Integer dishId,
	String userId,
	String dishName,
	Integer dishCategoryId,
	String dishCategoryName,
	List<Integer> optionIds,
	int price,
	int quantity,
	LocalDateTime orderedAt
) {

}
