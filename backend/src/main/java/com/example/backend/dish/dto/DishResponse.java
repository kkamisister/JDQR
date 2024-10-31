package com.example.backend.dish.dto;

import java.util.List;

import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishCategory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record DishResponse() {
	@Builder
	@Schema(name = "메뉴 요약조회 데이터", description = "메뉴 요약조회 전체 결과, 메뉴설정 페이지에 필요한 모든 데이터를"
		+ "담는다")
	public record DishSummaryResultDto(
		Integer dishNum,
		List<DishSummaryInfo> dishes
	) {

	}
	@Builder
	@Schema(name = "메뉴 요약정보", description = "각 메뉴들의 목록 정보, 메인페이지 메뉴 탭에 사용")
	public record DishSummaryInfo(
		Integer id,
		DishCategory dishCategory,
		String name,
		Integer price,
		String description,
		String image
	){
		public static DishSummaryInfo from(Dish dish) {
			return DishSummaryInfo.builder()
				.id(dish.getId())
				.dishCategory(dish.getDishCategory())
				.name(dish.getName())
				.price(dish.getPrice())
				.description(dish.getDescription())
				.image(dish.getImage())
				.build();
		}
	}
}
