package com.example.backend.order.repository;

import java.util.List;

import com.example.backend.order.entity.Order;

public interface OrderRepositoryCustom {
    /**
     * 테이블의 가장 최근 주문을 반환하는 메서드
     */
    Order findMostRecentOrder(String tableId);
}
