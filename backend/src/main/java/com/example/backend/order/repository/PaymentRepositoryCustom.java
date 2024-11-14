package com.example.backend.order.repository;

import com.example.backend.order.entity.ParentOrder;
import com.example.backend.order.entity.Payment;

import java.util.List;

public interface PaymentRepositoryCustom {

    List<Payment> findPaymentsByOrders(List<ParentOrder> parentOrders);
}
