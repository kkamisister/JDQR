package com.example.backend.dish.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.dish.entity.DishOptionGroup;

public interface DishOptionGroupRepository extends JpaRepository<DishOptionGroup, Integer> {
	List<DishOptionGroup> findByDishId(Integer dishId);
}
