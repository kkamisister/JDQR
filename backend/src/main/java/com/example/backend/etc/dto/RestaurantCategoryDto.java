package com.example.backend.etc.dto;

import static java.lang.Boolean.*;

import com.example.backend.common.enums.CategoryType;
import com.example.backend.etc.entity.RestaurantCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RestaurantCategoryDto {

	private int restaurantCategoryId;
	private String restaurantCategoryName;
	@JsonIgnore
	private Boolean isMajor;

	public static RestaurantCategoryDto from(RestaurantCategory restaurantCategory) {

		return RestaurantCategoryDto.builder()
			.restaurantCategoryId(restaurantCategory.getId())
			.restaurantCategoryName(restaurantCategory.getName())
			.isMajor(restaurantCategory.getCategoryType() == CategoryType.MAJOR ? TRUE : FALSE)
			.build();


	}

}
