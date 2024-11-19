package com.example.backend.dish.repository;

import java.util.List;
import java.util.Optional;

import com.example.backend.dish.entity.Dish;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.order.entity.ParentOrder;

public interface DishRepositoryCustom {
	List<Dish> findDishesByRestaurant(Restaurant restaurant);
	List<Dish> findDishesByKeyword(Restaurant restaurant, String keyword);
	List<Dish> findAllByOrder(ParentOrder parentOrder);
	Optional<Dish> findDishWithCategoryById(Integer dishId);
}
