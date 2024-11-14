package com.example.backend.dish.dto;

import java.util.List;

public record DishRequest() {

	public record DishInfo(
		String dishName,
		Integer dishCategoryId,
		String dishCategoryName,
		List<Integer> optionIds,
		Integer price,
		String description,
		String image,
		List<Integer> tagIds
	){

	}
}
