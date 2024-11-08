package com.example.backend.order.repository;


import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.order.entity.Order;
import com.example.backend.order.entity.OrderItem;

import java.util.List;

import static com.example.backend.order.entity.QOrderItem.orderItem;

public class OrderItemRepositoryCustomImpl extends Querydsl4RepositorySupport implements OrderItemRepositoryCustom {

    // Order에 해당하는 OrderItem 목록을 반환
    @Override
    public List<OrderItem> findOrderItemByOrder(List<Order> orders) {
        return selectFrom(orderItem)
            .where(orderItem.order.in(orders))
            .fetch();
    }
}
