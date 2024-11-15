package com.example.backend.order.repository;

import com.example.backend.order.entity.ParentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentOrderRepository extends JpaRepository<ParentOrder, Integer> {
    Optional<ParentOrder> findFirstByIdDesc();
}
