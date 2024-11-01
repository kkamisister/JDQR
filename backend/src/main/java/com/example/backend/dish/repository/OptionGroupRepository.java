package com.example.backend.dish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.dish.entity.OptionGroup;

public interface OptionGroupRepository extends JpaRepository<OptionGroup, Integer> {
}
