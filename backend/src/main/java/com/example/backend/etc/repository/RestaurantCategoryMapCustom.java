package com.example.backend.etc.repository;

import java.util.List;
import java.util.Optional;

import com.example.backend.etc.entity.RestaurantCategoryMap;

public interface RestaurantCategoryMapCustom {

	List<RestaurantCategoryMap> findByRestaurandIds(List<Integer> restaurantIds);
	List<RestaurantCategoryMap> findByRestaurantId(Integer restaurantId);
}
