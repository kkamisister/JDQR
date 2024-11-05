package com.example.backend.etc.dto;

import com.example.backend.etc.entity.RestaurantCategory;

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

	public static RestaurantCategoryDto from(RestaurantCategory restaurantCategory) {

		return RestaurantCategoryDto.builder()
			.restaurantCategoryId(restaurantCategory.getId())
			.restaurantCategoryName(restaurantCategory.getName())
			.build();


	}

}
