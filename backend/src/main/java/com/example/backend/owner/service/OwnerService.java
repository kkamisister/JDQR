package com.example.backend.owner.service;

import static com.example.backend.common.dto.CommonResponse.*;
import static com.example.backend.dish.dto.DishResponse.*;

import com.example.backend.common.dto.CommonResponse;
import com.example.backend.dish.dto.DishRequest;
import com.example.backend.dish.dto.DishResponse;

public interface OwnerService {

	ResponseWithMessage addDish(Integer userId, DishRequest.DishInfo dishInfo);
	ResponseWithMessage removeDish(Integer userId, Integer dishId);
	ResponseWithMessage updateDish(Integer userId, Integer dishId, DishRequest.DishInfo dishInfo);

	DishSummaryResultDto getAllMenus(Integer userId);
	DishSummaryResultDto getMenu(Integer userId,Integer dishId);

}
