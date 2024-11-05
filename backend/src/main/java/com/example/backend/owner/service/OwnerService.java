package com.example.backend.owner.service;

import com.example.backend.common.dto.CommonResponse;
import com.example.backend.dish.dto.DishRequest;

public interface OwnerService {

	CommonResponse.ResponseWithMessage addDish(Integer userId, DishRequest.DishInfo dishInfo);
	CommonResponse.ResponseWithMessage removeDish(Integer userId, Integer dishId);
	CommonResponse.ResponseWithMessage updateDish(Integer userId, Integer dishId, DishRequest.DishInfo dishInfo);
}
