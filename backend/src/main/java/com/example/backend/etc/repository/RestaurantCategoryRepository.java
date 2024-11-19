package com.example.backend.etc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.etc.entity.RestaurantCategory;

public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, Integer>, RestaurantCategoryRepositoryCustom{


}
