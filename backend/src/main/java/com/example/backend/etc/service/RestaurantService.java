package com.example.backend.etc.service;

import static com.example.backend.etc.dto.RestaurantResponse.*;

import org.springframework.web.multipart.MultipartFile;

import com.example.backend.common.dto.CommonResponse;
import com.example.backend.dish.dto.DishRequest;
import com.example.backend.etc.dto.RestaurantDto;
import com.example.backend.etc.dto.RestaurantProfileDto;
import com.example.backend.etc.dto.RestaurantResponse;
import com.example.backend.etc.dto.RestaurantResponse.RestaurantInfo;

public interface RestaurantService {

	RestaurantInfo getNearRestaurant(double minLat,double maxLat,double minLng,double maxLng,int people,boolean together);
	RestaurantProfileDto getRestaurant(Integer userId);
	void createRestaurant(RestaurantProfileDto restaurantProfile, MultipartFile imageFile,Integer userId);

	RestaurantDetailInfo getRestaurantDetail(Integer restaurantId);

	void updateBusinessStatus(Integer userId);
	RestaurantBusinessDto getBusinessStatus(Integer userId);

	RestaurantInfo searchByKeyword(String keyword,double minLat,double maxLat,double minLng,double maxLng,int people,boolean together);

}
