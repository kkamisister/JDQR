package com.example.backend.owner.service;

import com.example.backend.common.dto.CommonResponse.*;
import com.example.backend.dish.dto.DishRequest.*;
import com.example.backend.owner.dto.OwnerResponse.*;

public interface OwnerService {

	ResponseWithMessage addDish(Integer userId, DishInfo dishInfo);
	ResponseWithMessage removeDish(Integer userId, Integer dishId);
	ResponseWithMessage updateDish(Integer userId, Integer dishId, DishInfo dishInfo);

    WholeOptionResponseDto getWholeOptionInfo(Integer userId);
}
