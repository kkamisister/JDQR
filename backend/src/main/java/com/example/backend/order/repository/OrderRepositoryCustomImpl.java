package com.example.backend.order.repository;

import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.order.entity.Order;
import com.example.backend.order.entity.QOrder;

import static com.example.backend.order.entity.QOrder.order;

import java.util.List;

public class OrderRepositoryCustomImpl extends Querydsl4RepositorySupport implements OrderRepositoryCustom {
    @Override
    public Order findMostRecentOrder(String tableId) {
        return selectFrom(order)
            .where(order.tableId.eq(tableId))
            .orderBy(order.id.desc())
            .fetchOne();
    }

}
