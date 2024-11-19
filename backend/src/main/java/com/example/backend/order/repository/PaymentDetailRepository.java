package com.example.backend.order.repository;

import com.example.backend.order.entity.Payment;
import com.example.backend.order.entity.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Integer> {
    List<PaymentDetail> findAllByPayment(Payment payment);
}
