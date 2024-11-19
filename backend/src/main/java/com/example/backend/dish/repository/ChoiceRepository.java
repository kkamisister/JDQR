package com.example.backend.dish.repository;

import java.util.List;

import com.example.backend.dish.entity.Choice;
import com.example.backend.dish.entity.Option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Integer> {
	List<Choice> findByOption(Option option);
}
