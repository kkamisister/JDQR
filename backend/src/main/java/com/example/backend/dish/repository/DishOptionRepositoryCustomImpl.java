package com.example.backend.dish.repository;

import static com.example.backend.dish.entity.QDishOption.*;
import static com.example.backend.dish.entity.QOption.*;

import java.util.List;

import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishOption;
import com.example.backend.dish.entity.QChoice;

public class DishOptionRepositoryCustomImpl extends Querydsl4RepositorySupport implements DishOptionRepositoryCustom {
	@Override
	public List<DishOption> findByDish(Dish dish) {

		return selectFrom(dishOption)
			.join(dishOption.option, option).fetchJoin()
			.join(option.choices, QChoice.choice).fetchJoin()
			.where(dishOption.dish.eq(dish))
			.fetch();
	}
}
