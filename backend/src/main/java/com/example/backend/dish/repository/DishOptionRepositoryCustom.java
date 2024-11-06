package com.example.backend.dish.repository;

import java.util.List;

import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishOption;

public interface DishOptionRepositoryCustom {

	List<DishOption> findByDish(Dish dish);
}
