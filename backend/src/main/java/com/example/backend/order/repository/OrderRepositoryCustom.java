package com.example.backend.order.repository;

import com.example.backend.order.entity.Order;

public interface OrderRepositoryCustom {
    /**
     * 가장 최근 주문을
     */
    Order findMostRecentOrder(String tableId);
}
