package com.example.backend.dish.repository;

import static com.example.backend.dish.entity.QDishTag.*;
import static com.example.backend.dish.entity.QTag.*;

import java.util.List;

import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishTag;
import com.example.backend.dish.entity.QTag;
import com.example.backend.dish.entity.Tag;

public class DishTagRepositoryCustomImpl extends Querydsl4RepositorySupport implements DishTagRepositoryCustom {
	@Override
	public List<DishTag> findTagsByDish(Dish dish) {

		return selectFrom(dishTag)
			.join(dishTag.tag, tag).fetchJoin()
			.where(dishTag.dish.eq(dish))
			.fetch();
	}
}
