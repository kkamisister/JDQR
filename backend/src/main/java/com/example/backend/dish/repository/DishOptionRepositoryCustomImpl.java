package com.example.backend.dish.repository;

import static com.example.backend.common.enums.EntityStatus.*;
import static com.example.backend.dish.entity.QDishOption.*;
import static com.example.backend.dish.entity.QOption.*;

import java.util.List;

import com.example.backend.common.enums.EntityStatus;
import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishOption;
import com.example.backend.dish.entity.Option;
import com.example.backend.dish.entity.QChoice;
import com.example.backend.dish.entity.QOption;

public class DishOptionRepositoryCustomImpl extends Querydsl4RepositorySupport implements DishOptionRepositoryCustom {
	@Override
	public List<DishOption> findByDish(Dish dish) {

		return selectFrom(dishOption)
			.join(dishOption.option, option).fetchJoin()
			.join(option.choices, QChoice.choice).fetchJoin()
			.where(dishOption.dish.eq(dish).and(dishOption.status.eq(ACTIVE)))
			.fetch();
	}

	@Override
	public List<DishOption> findByOption(Option option) {
		return selectFrom(dishOption)
			.join(dishOption.option, QOption.option).fetchJoin()
			.where(dishOption.option.eq(option).and(dishOption.status.eq(ACTIVE)))
			.fetch();
	}
}
