package com.example.backend.dish.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.dish.entity.DishTag;

public interface DishTagRepository extends JpaRepository<DishTag, Integer>, DishTagRepositoryCustom {
	List<DishTag> findByDishId(Integer dishId);
}
