package com.example.backend.order.repository;

import com.example.backend.order.entity.Order;
import com.example.backend.order.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findAllByOrder(Order order);
}
