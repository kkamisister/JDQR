package com.example.backend.order.repository;

import com.example.backend.order.dto.OrderResponseVo;
import com.example.backend.order.entity.Order;
import com.example.backend.order.entity.Payment;

import java.util.Collection;
import java.util.List;

public interface PaymentRepositoryCustom {

    List<Payment> findPaymentsByOrders(List<Order> orders);
}
