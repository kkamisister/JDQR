package com.example.backend.order.repository;

import java.util.List;

import com.example.backend.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, OrderRepositoryCustom {
	List<Order> findByTableId(String tableId);
}
