package com.example.backend.order.repository;

import com.example.backend.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>,OrderItemRepositoryCustom{


}
