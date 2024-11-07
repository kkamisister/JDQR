package com.example.backend.order.repository;

import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.order.dto.OrderResponseVo;
import com.example.backend.order.entity.Order;
import com.example.backend.order.enums.OrderStatus;
import com.querydsl.core.types.Projections;

import java.util.List;

import static com.example.backend.order.entity.QOrder.order;
import static com.example.backend.order.entity.QOrderItem.orderItem;
import static com.example.backend.order.entity.QOrderItemOption.orderItemOption;
import static com.example.backend.dish.entity.QChoice.choice;
import static com.example.backend.dish.entity.QOption.option;
import static com.example.backend.dish.entity.QDish.dish;
import static com.example.backend.dish.entity.QDishCategory.dishCategory;

public class OrderRepositoryCustomImpl extends Querydsl4RepositorySupport implements OrderRepositoryCustom {
    @Override
    public Order findMostRecentOrder(String tableId) {
        return selectFrom(order)
            .where(order.tableId.eq(tableId))
            .orderBy(order.id.desc())
            .fetchOne();
    }

    @Override
    public List<OrderResponseVo> findWholeOrderInfos(String tableId) {
        return select(Projections.bean(OrderResponseVo.class, order.id, dish.id, orderItem.userId,
            dish.name, dish.price, dishCategory.id, dishCategory.name, orderItem.quantity, option.id,
            option.name, choice.id, choice.name, choice.price
        ))
            .from(order)
            .join(orderItem)
            .on(order.eq(orderItem.order))
            .join(orderItemOption)
            .on(orderItem.eq(orderItemOption.orderItem))
            .join(choice)
            .on(orderItemOption.choice.eq(choice))
            .join(option)
            .on(choice.option.eq(option))
            .join(dish)
            .on(orderItem.dish.eq(dish))
            .join(dishCategory)
            .on(dish.dishCategory.eq(dishCategory))
            .fetch()
            ;
    }
}
