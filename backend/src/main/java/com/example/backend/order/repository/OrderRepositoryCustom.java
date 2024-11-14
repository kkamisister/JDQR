package com.example.backend.order.repository;

import com.example.backend.order.dto.OrderResponseVo;
import com.example.backend.order.entity.ParentOrder;
import com.example.backend.order.entity.Payment;

import java.util.List;

public interface OrderRepositoryCustom {
    /**
     * 테이블의 가장 최근 주문을 반환하는 메서드
     */
    List<ParentOrder> findUnpaidOrders(String tableId);

    List<OrderResponseVo> findWholeOrderInfos(String tableId);

    List<ParentOrder> findOrdersByPayment(Payment payment);
}
