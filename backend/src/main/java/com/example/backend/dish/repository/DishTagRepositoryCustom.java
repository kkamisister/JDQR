package com.example.backend.dish.repository;

import java.util.List;

import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishTag;

public interface DishTagRepositoryCustom {

	List<DishTag> findTagsByDish(Dish dish);

}
