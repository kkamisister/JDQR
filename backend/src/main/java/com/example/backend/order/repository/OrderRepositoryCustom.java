package com.example.backend.order.repository;

import com.example.backend.order.dto.OrderResponseVo;
import com.example.backend.order.entity.Order;
import com.example.backend.order.entity.Payment;

import java.util.List;

public interface OrderRepositoryCustom {
    /**
     * 테이블의 가장 최근 주문을 반환하는 메서드
     */
    List<Order> findUnpaidOrders(String tableId);

    List<OrderResponseVo> findWholeOrderInfos(String tableId);

    List<Order> findOrdersByPayment(Payment payment);
}
