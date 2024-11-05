package com.example.backend.dish.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.dish.entity.Dish;
import com.example.backend.owner.entity.Owner;

public interface DishRepository extends JpaRepository<Dish, Integer>, DishRepositoryCustom {

}
