package com.example.backend.etc.repository;

import java.util.Optional;

import com.example.backend.etc.entity.Restaurant;
import com.example.backend.owner.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant,Integer>,RestaurantRepositoryCustom{


	Optional<Restaurant> findByOwner(Owner owner);
	Optional<Restaurant> findByOwnerId(int ownerId);
}
