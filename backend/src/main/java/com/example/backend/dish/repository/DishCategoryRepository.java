package com.example.backend.dish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.dish.entity.DishCategory;

public interface DishCategoryRepository extends JpaRepository<DishCategory, Integer> {
}
