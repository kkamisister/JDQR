package com.example.backend.etc.repository;

import java.util.List;

import com.example.backend.etc.entity.RestaurantCategoryMap;

public interface RestaurantCategoryMapCustom {

	List<RestaurantCategoryMap> findByRestaurandIds(List<Integer> restaurantIds);
}
