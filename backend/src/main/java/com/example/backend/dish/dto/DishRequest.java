package com.example.backend.dish.dto;

import java.util.List;

public record DishRequest() {

	public record DishInfo(
		String dishName,
		int dishCategoryId,
		String dishCategory,
		int optionGroupId,
		List<Integer> optionIds,
		int price,
		String description,
		String image,
		List<Integer> tagIds
	){

	}
}
