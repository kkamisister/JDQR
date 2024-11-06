package com.example.backend.etc.dto;

import java.util.List;

import com.example.backend.dish.dto.DishResponse;
import com.example.backend.dish.dto.DishResponse.DishDataDto;

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

	@Schema(name = "식당 상세 데이터",description = "식당 상세조회 데이터")
	@Builder
	public record RestaurantDetailInfo(
		RestaurantDto restaurant,
		DishDataDto dishInfo
	){
		public static RestaurantDetailInfo of(RestaurantDto restaurant, DishDataDto dishInfo) {
			return RestaurantDetailInfo.builder()
				.restaurant(restaurant)
				.dishInfo(dishInfo)
				.build();
		}

	}
}
