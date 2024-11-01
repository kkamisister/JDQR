package com.example.backend.dish.service;

import static com.example.backend.dish.dto.DishRequest.*;
import static com.example.backend.dish.dto.DishResponse.*;

import com.example.backend.common.dto.CommonResponse.ResponseWithMessage;
import com.example.backend.common.dto.CommonResponse.ResponseWithData;

public interface DishService {

	ResponseWithMessage addDish(Integer userId, DishInfo dishInfo);
	ResponseWithMessage removeDish(Integer userId, Integer dishId);

}
