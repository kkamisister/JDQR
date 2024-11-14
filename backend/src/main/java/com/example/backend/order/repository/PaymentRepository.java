package com.example.backend.order.repository;

import com.example.backend.order.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>, PaymentRepositoryCustom {
    Payment findByTossOrderId(String tossOrderId);
}
