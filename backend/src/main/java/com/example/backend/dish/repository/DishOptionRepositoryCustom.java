package com.example.backend.dish.repository;

import java.util.List;

import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishOption;
import com.example.backend.dish.entity.Option;

public interface DishOptionRepositoryCustom {

	List<DishOption> findByDish(Dish dish);
	List<DishOption> findByOption(Option option);
}
