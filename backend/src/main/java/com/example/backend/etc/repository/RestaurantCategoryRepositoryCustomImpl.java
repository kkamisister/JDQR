package com.example.backend.etc.repository;

import static com.example.backend.etc.entity.QRestaurantCategory.*;

import java.util.List;

import com.example.backend.common.enums.CategoryType;
import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.etc.entity.QRestaurantCategory;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.etc.entity.RestaurantCategory;

public class RestaurantCategoryRepositoryCustomImpl extends Querydsl4RepositorySupport implements RestaurantCategoryRepositoryCustom {

	@Override
	public List<RestaurantCategory> findAllMajor() {
		return selectFrom(restaurantCategory)
			.where(restaurantCategory.categoryType.eq(CategoryType.MAJOR))
			.fetch();
	}
}
