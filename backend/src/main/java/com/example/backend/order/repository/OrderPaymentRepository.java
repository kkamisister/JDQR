package com.example.backend.order.repository;

import com.example.backend.order.entity.ParentOrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPaymentRepository extends JpaRepository<ParentOrderPayment, Integer> {

}
