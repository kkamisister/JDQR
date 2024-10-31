package com.example.backend.order.repository;

import com.example.backend.order.entity.OrderItemOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemOptionRepository extends JpaRepository<OrderItemOption, Integer> {
}
