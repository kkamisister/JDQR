package com.example.backend.dish.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.dish.entity.DishOption;

public interface DishOptionRepository extends JpaRepository<DishOption, Integer>, DishOptionRepositoryCustom{
	List<DishOption> findByDishId(Integer dishId);
}
