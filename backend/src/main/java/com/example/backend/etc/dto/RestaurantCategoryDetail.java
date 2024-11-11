package com.example.backend.etc.dto;

import java.util.List;

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
public class RestaurantCategoryDetail {

	private RestaurantCategoryDto major;
	private List<RestaurantCategoryDto> minor;

	public static RestaurantCategoryDetail of(RestaurantCategoryDto major, List<RestaurantCategoryDto> minor) {
		return RestaurantCategoryDetail.builder()
			.major(major)
			.minor(minor)
			.build();
	}

}
