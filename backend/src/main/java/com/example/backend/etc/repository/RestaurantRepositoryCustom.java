package com.example.backend.etc.repository;

import java.util.List;

import com.example.backend.etc.entity.Restaurant;

public interface RestaurantRepositoryCustom {

	List<Restaurant> findByCoord(double minLat, double maxLat, double minLng, double maxLng);

	List<Restaurant> findByKeyword(String keyword);
}
