package com.example.backend.dish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.dish.entity.Option;

public interface OptionRepository extends JpaRepository<Option, Integer> {
}
