package com.example.backend.etc.repository;

import static com.example.backend.etc.entity.QRestaurant.*;
import static com.example.backend.etc.entity.QRestaurantCategory.*;
import static com.example.backend.etc.entity.QRestaurantCategoryMap.*;

import java.util.List;

import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.etc.entity.QRestaurant;
import com.example.backend.etc.entity.QRestaurantCategory;
import com.example.backend.etc.entity.QRestaurantCategoryMap;
import com.example.backend.etc.entity.RestaurantCategoryMap;

public class RestaurantCategoryMapCustomImpl extends Querydsl4RepositorySupport implements RestaurantCategoryMapCustom {

	@Override
	public List<RestaurantCategoryMap> findByRestaurandIds(List<Integer> restaurantIds) {

		return selectFrom(restaurantCategoryMap)
			.join(restaurantCategoryMap.restaurant, restaurant).fetchJoin()
			.join(restaurantCategoryMap.restaurantCategory, restaurantCategory).fetchJoin()
			.where(restaurant.id.in(restaurantIds))
			.fetch();
	}
}
