package com.example.backend.owner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.owner.entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {

	Optional<Owner> findByEmail(String email);
}
