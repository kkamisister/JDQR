package com.example.backend.etc.repository;

import static com.example.backend.dish.entity.QDishCategory.*;
import static com.example.backend.etc.entity.QRestaurant.*;
import static com.example.backend.etc.entity.QRestaurantCategoryMap.*;

import java.util.List;

import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.QDishCategory;
import com.example.backend.etc.entity.QRestaurant;
import com.example.backend.etc.entity.QRestaurantCategoryMap;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.etc.entity.RestaurantCategoryMap;
import com.querydsl.core.types.dsl.CaseBuilder;

public class RestaurantRepositoryCustomImpl extends Querydsl4RepositorySupport implements RestaurantRepositoryCustom{

	/**
	 * 해당 범위내에 있는 식당을 가져오는 메서드
	 * @param minLat
	 * @param maxLat
	 * @param minLng
	 * @param maxLng
	 * @return
	 */
	@Override
	public List<Restaurant> findByCoord(double minLat, double maxLat, double minLng, double maxLng) {

		return selectFrom(restaurant)
			.where(restaurant.latitude.between(minLat,maxLat))
			.where(restaurant.longitude.between(minLng,maxLng))
			.fetch();
	}

	/**
	 * 키워드에 해당하는 음식점을 검색하는 메서드
	 * @param keyword
	 * @return
	 */
	@Override
	public List<Restaurant> findByKeyword(String keyword) {

		return select(restaurant)
			.from(restaurantCategoryMap)
			.join(restaurantCategoryMap.restaurant, restaurant)
			.where(restaurant.name.likeIgnoreCase("%" + keyword + "%")
				.or(restaurantCategoryMap.restaurantCategory.name.likeIgnoreCase("%" + keyword + "%")))
			.orderBy(new CaseBuilder()
				.when(restaurant.name.eq(keyword)).then(0)
				.when(restaurantCategoryMap.restaurantCategory.name.eq(keyword)).then(1)
				.when(restaurant.name.startsWithIgnoreCase(keyword)).then(2)
				.when(restaurantCategoryMap.restaurantCategory.name.startsWithIgnoreCase(keyword)).then(3)
				.when(restaurant.name.containsIgnoreCase(keyword)).then(4)
				.when(restaurantCategoryMap.restaurantCategory.name.containsIgnoreCase(keyword)).then(5)
				.when(restaurant.name.endsWithIgnoreCase(keyword)).then(6)
				.when(restaurantCategoryMap.restaurantCategory.name.endsWithIgnoreCase(keyword)).then(7)
				.otherwise(8).asc()
			)
			.limit(10)
			.fetch();
	}


}
