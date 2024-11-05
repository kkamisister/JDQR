package com.example.backend.etc.service;

import com.example.backend.etc.dto.RestaurantProfileDto;
import com.example.backend.etc.dto.RestaurantResponse.RestaurantInfo;

public interface RestaurantService {

	RestaurantInfo getNearRestaurant(double minLat,double maxLat,double minLng,double maxLng,int people,boolean together);
	RestaurantProfileDto getRestaurant(Integer restaurantId,Integer userId);
	void createRestaurant(RestaurantProfileDto restaurantProfile,Integer userId);

}
