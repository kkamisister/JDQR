package com.example.backend.restaurants.repository;

import java.util.Optional;

import com.example.backend.owner.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.restaurants.entity.Restaurants;
public interface RestaurantsRepository extends JpaRepository<Restaurants,Integer> {


	Optional<Restaurants> findByOwner(Owner owner);

}
