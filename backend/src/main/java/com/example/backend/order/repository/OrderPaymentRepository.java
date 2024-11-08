package com.example.backend.order.repository;

import com.example.backend.order.entity.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Integer> {

}
