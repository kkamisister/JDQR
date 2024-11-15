package com.example.backend.order.repository;

import java.util.Collection;
import java.util.List;

import com.example.backend.order.entity.Order;
import com.example.backend.order.entity.OrderItem;

public interface OrderItemRepositoryCustom {
    List<OrderItem> findOrderItemByOrder(List<Order> orders);
}
