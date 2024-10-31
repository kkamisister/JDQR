package com.example.backend.dish.service;

import static com.example.backend.dish.dto.DishResponse.*;

import com.example.backend.common.dto.CommonResponse;
import com.example.backend.common.dto.CommonResponse.ResponseWithData;
import com.example.backend.dish.dto.DishResponse;
import com.example.backend.dish.entity.Dish;

public interface DishService {

	void addDish(Dish dish,Integer userId);
	ResponseWithData<DishSummaryResultDto> getAllDishes(Integer userId);

}
