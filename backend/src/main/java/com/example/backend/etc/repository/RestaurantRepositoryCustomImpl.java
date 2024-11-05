package com.example.backend.etc.repository;

import static com.example.backend.etc.entity.QRestaurant.*;

import java.util.List;

import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.etc.entity.QRestaurant;
import com.example.backend.etc.entity.Restaurant;

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
}
