package com.example.backend.dish.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.dish.entity.Dish;

public interface DishRepository extends JpaRepository<Dish, Integer>, DishRepositoryCustom {

}
