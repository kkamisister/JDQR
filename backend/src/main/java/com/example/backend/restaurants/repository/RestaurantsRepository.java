package com.example.backend.restaurants.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.restaurants.entity.Restaurants;
import com.example.backend.user.entity.User;

public interface RestaurantsRepository extends JpaRepository<Restaurants,Integer> {


	Optional<Restaurants> findByUser(User user);

}
