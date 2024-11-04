package com.example.backend.etc.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestaurantCategoryDto {

	private int restaurantCategoryId;
	private String restaurantCategoryName;

}
