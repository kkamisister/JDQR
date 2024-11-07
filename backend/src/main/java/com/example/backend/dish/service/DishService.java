package com.example.backend.dish.service;

import static com.example.backend.dish.dto.DishResponse.*;

public interface DishService {

	DishSummaryResultDto getAllDishes(String tableId);
	DishDetailInfo getDish(Integer dishId,String tableId);
	DishSearchResultDto getSearchedDishes(String keyword, Integer restaurantId);

}
