package com.example.backend.etc.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record RestaurantResponse() {

	@Schema(name = "식당 데이터",description = "유저의 화면상에 있는 식당 데이터")
	@Builder
	public record RestaurantInfo(
		List<String> majorCategories,
		List<RestaurantDto> restaurants
	){


	}
}
