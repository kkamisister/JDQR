package com.example.backend.dish.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.dish.entity.Dish;

public interface DishRepository extends JpaRepository<Dish, Integer> {
	List<Dish> getAllDishes();
}
