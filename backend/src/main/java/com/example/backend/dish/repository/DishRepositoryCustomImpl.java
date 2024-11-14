package com.example.backend.dish.repository;

import static com.example.backend.dish.entity.QDish.*;
import static com.example.backend.dish.entity.QDishCategory.*;
import static com.example.backend.order.entity.QOrder.*;
import static com.example.backend.order.entity.QOrderItem.*;

import java.util.List;
import java.util.Optional;

import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.dish.entity.Dish;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.order.entity.ParentOrder;
import com.example.backend.order.entity.OrderItem;

public class DishRepositoryCustomImpl extends Querydsl4RepositorySupport implements DishRepositoryCustom {
	@Override
	public List<Dish> findDishesByRestaurant(Restaurant restaurant) {
		return selectFrom(dish)
			.join(dish.dishCategory, dishCategory).fetchJoin()
			.where(dishCategory.restaurant.eq(restaurant))
			.fetch();
	}
	@Override
	public List<Dish> findDishesByKeyword(Restaurant restaurant, String keyword) {
		return selectFrom(dish)
			.join(dish.dishCategory, dishCategory).fetchJoin()
			.where(dishCategory.restaurant.eq(restaurant).and(dish.name.containsIgnoreCase(keyword)))
			.fetch();
	}

	@Override
	public List<Dish> findAllByOrder(ParentOrder parentOrder1) {
		List<OrderItem> orderItems = selectFrom(orderItem)
			.join(orderItem.dish, dish)
			.join(orderItem.order, order).fetchJoin()
			.where(order.eq(parentOrder1))
			.orderBy(dish.id.asc())
			.fetch();

		List<Dish> dishes = orderItems.stream().map(OrderItem::getDish).toList();

		return dishes;
	}

	@Override
	public Optional<Dish> findDishWithCategoryById(Integer dishId) {
		Dish dish1 = selectFrom(dish)
			.join(dish.dishCategory, dishCategory).fetchJoin()
			.where(dish.id.eq(dishId))
			.fetchOne();

		return Optional.ofNullable(dish1);
	}
}
