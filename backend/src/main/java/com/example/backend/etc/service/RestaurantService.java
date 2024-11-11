package com.example.backend.etc.service;

import static com.example.backend.etc.dto.RestaurantResponse.*;

import com.example.backend.common.dto.CommonResponse;
import com.example.backend.dish.dto.DishRequest;
import com.example.backend.etc.dto.RestaurantDto;
import com.example.backend.etc.dto.RestaurantProfileDto;
import com.example.backend.etc.dto.RestaurantResponse;
import com.example.backend.etc.dto.RestaurantResponse.RestaurantInfo;

public interface RestaurantService {

	RestaurantInfo getNearRestaurant(double minLat,double maxLat,double minLng,double maxLng,int people,boolean together);
	RestaurantProfileDto getRestaurant(Integer restaurantId,Integer userId);
	void createRestaurant(RestaurantProfileDto restaurantProfile,Integer userId);

	RestaurantDetailInfo getRestaurantDetail(Integer restaurantId);

	void updateBusinessStatus(Integer restaurantId);
	RestaurantDto getBusinessStatus(Integer restaurantId);

	RestaurantInfo searchByKeyword(String keyword,double minLat,double maxLat,double minLng,double maxLng,int people,boolean together);

}
