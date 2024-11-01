package com.example.backend.dish.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.dish.entity.DishOptionGroup;
import com.example.backend.dish.entity.DishTag;

public interface DishTagRepository extends JpaRepository<DishTag, Integer> {
	List<DishTag> findByDishId(Integer dishId);
}
