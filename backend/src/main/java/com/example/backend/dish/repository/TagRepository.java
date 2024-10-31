package com.example.backend.dish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.dish.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
