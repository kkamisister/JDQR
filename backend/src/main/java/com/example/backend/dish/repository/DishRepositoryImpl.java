package com.example.backend.dish.repository;

import java.util.List;

import javax.management.Query;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.example.backend.dish.entity.Dish;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Primary
@RequiredArgsConstructor
@Slf4j
public class DishRepositoryImpl implements DishRepository {

	@Override
	public List<Dish> findAll() {
		Query query = new Query();

		return
	}
}
