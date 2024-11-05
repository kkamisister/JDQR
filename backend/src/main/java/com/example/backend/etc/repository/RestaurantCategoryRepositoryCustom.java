package com.example.backend.etc.repository;

import java.util.List;

import com.example.backend.etc.entity.RestaurantCategory;

public interface RestaurantCategoryRepositoryCustom {

	List<RestaurantCategory> findAllMajor();


}
