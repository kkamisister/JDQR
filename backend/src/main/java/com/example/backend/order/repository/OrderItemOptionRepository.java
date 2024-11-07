package com.example.backend.order.repository;

import com.example.backend.order.entity.OrderItemChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemOptionRepository extends JpaRepository<OrderItemChoice, Integer> {
}
