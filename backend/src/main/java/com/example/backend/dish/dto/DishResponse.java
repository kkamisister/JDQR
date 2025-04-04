package com.example.backend.dish.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishCategory;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record DishResponse() {
	@Builder
	@Schema(name = "메뉴 요약조회 데이터", description = "메뉴 요약조회 전체 결과, 메뉴설정 페이지에 필요한 모든 데이터를"
		+ "담는다")
	public record DishSummaryResultDto(
		@JsonInclude(JsonInclude.Include.NON_NULL)
		String tableId,
		@JsonInclude(JsonInclude.Include.NON_NULL)
		String restaurantName,
		@JsonInclude(JsonInclude.Include.NON_NULL)
		String tableName,
		@JsonInclude(JsonInclude.Include.NON_NULL)
		Integer peopleCount,
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
		List<String> tags,
		@JsonInclude(JsonInclude.Include.NON_NULL)
		List<OptionDto> options
	){

		public static DishSimpleInfo of(Dish dish,List<String> tags,List<OptionDto> options){
			return DishSimpleInfo.builder()
				.dishId(dish.getId())
				.dishName(dish.getName())
				.price(dish.getPrice())
				.description(dish.getDescription())
				.image(dish.getImage())
				.tags(tags)
				.options(options)
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
		@JsonInclude(JsonInclude.Include.NON_NULL)
		List<OptionDto> options,
		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		List<String> tags,
		@JsonInclude(JsonInclude.Include.NON_NULL)
		Integer quantity,
		Integer menuPrice
	){
		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			DishDetailInfo that = (DishDetailInfo)o;
			return dishId == that.dishId && Objects.equals(options, that.options);
		}

		@Override
		public int hashCode() {
			return Objects.hash(dishId,options);
		}

		// quantity를 설정할 수 있는 withQuantity 메서드 추가
		public DishDetailInfo withQuantity(int quantity) {
			return new DishDetailInfo(
				this.dishId,
				this.dishName,
				this.price,
				this.description,
				this.image,
				this.options,
				this.tags,
				quantity,
				this.menuPrice
			);
		}

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

		public static DishDetailInfo of(Dish dish,List<OptionDto> options){

			Integer menuPrice = dish.getPrice();
			for(OptionDto option : options){
				List<ChoiceDto> choices = option.getChoices();
				for(ChoiceDto choice : choices){
					menuPrice += choice.getPrice();
				}
			}

			return DishDetailInfo.builder()
				.dishId(dish.getId())
				.dishName(dish.getName())
				.price(dish.getPrice())
				.description(dish.getDescription())
				.image(dish.getImage())
				.options(options)
				.tags(Collections.emptyList())
				.menuPrice(menuPrice)
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

	@Builder
	@Schema(name = "메뉴 검색 결과 데이터", description = "메뉴 검색 결과를 반환하는 DTO")
	public record DishSearchResultDto(
		List<DishSimpleInfo> dishes
	) {
	}

}
