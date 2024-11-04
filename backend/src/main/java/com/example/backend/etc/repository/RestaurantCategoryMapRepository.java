package com.example.backend.etc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.etc.entity.RestaurantCategoryMap;

public interface RestaurantCategoryMapRepository extends JpaRepository<RestaurantCategoryMap, Integer>, RestaurantCategoryMapCustom{

}
