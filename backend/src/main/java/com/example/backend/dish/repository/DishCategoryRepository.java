package com.example.backend.dish.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.dish.entity.DishCategory;
import com.example.backend.etc.entity.Restaurant;

public interface DishCategoryRepository extends JpaRepository<DishCategory, Integer> {
	List<DishCategory> findByRestaurant(Restaurant restaurant);
}
