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
		String tableId,
		String tableName,
		int peopleCount,
		List<String> dishCategories,
		List<DishSummaryInfo> dishes
	) {

	}


	@Builder
	@Schema(name = "메뉴 요약정보", description = "각 메뉴들의 목록 정보, 메인페이지 메뉴 탭에 사용")
	public record DishSummaryInfo(
		int dishCategoryId,
		String dishCategoryName,
		List<DishSimpleInfo> items
	){
	}

	@Builder
	@Schema(name = "메뉴 상세요약정보", description = "각 메뉴들의 상세목록 정보, 메뉴 상세정보 탭에 사용")
	public record DishDetailSummaryInfo(
		int dishCategoryId,
		String dishCategoryName,
		List<DishDetailInfo> items
	){
	}

	@Builder
	@Schema(name = "메뉴에 대한 간단정보",description = "메뉴에 대한 간단한 정보를 담는 DTO")
	public record DishSimpleInfo(
		int dishId,
		String dishName,
		int price,
		String description,
		String image,
		List<String> tags
	){

		public static DishSimpleInfo of(Dish dish,List<String> tags){
			return DishSimpleInfo.builder()
				.dishId(dish.getId())
				.dishName(dish.getName())
				.price(dish.getPrice())
				.description(dish.getDescription())
				.image(dish.getImage())
				.tags(tags)
				.build();
		}
	}

	@Builder
	@Schema(name = "메뉴상세정보",description = "메뉴상세정보를 반환하는 DTO")
	public record DishDetailInfo(
		int dishId,
		String dishName,
		int price,
		String description,
		String image,
		List<OptionDto> options,
		List<String> tags
	){
		public static DishDetailInfo of(Dish dish,List<OptionDto> options,List<String> tags){
			return DishDetailInfo.builder()
				.dishId(dish.getId())
				.dishName(dish.getName())
				.price(dish.getPrice())
				.description(dish.getDescription())
				.image(dish.getImage())
				.options(options)
				.tags(tags)
				.build();
		}
	}

	@Builder
	public record DishDataDto(
		List<String> dishCategories,
		List<DishDetailSummaryInfo> dishes
	){
		public static DishDataDto of(List<String> dishCategories, List<DishDetailSummaryInfo> dishes){
			return DishDataDto.builder()
				.dishCategories(dishCategories)
				.dishes(dishes)
				.build();
		}
	}


}
