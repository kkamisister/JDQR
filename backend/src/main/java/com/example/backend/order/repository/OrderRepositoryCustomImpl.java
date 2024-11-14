package com.example.backend.order.repository;

import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.order.dto.OrderResponseVo;
import com.example.backend.order.entity.ParentOrder;
import com.example.backend.order.entity.Payment;
import com.example.backend.order.enums.OrderStatus;
import com.querydsl.core.types.Projections;

import java.util.List;

import static com.example.backend.order.entity.QOrder.order;
import static com.example.backend.order.entity.QOrderItem.orderItem;
import static com.example.backend.order.entity.QOrderItemChoice.orderItemChoice;
import static com.example.backend.order.entity.QOrderPayment.orderPayment;
import static com.example.backend.order.entity.QPayment.payment;
import static com.example.backend.dish.entity.QChoice.choice;
import static com.example.backend.dish.entity.QOption.option;
import static com.example.backend.dish.entity.QDish.dish;
import static com.example.backend.dish.entity.QDishCategory.dishCategory;

public class OrderRepositoryCustomImpl extends Querydsl4RepositorySupport implements OrderRepositoryCustom {
    @Override
    public List<ParentOrder> findUnpaidOrders(String tableId) {
        return selectFrom(order)
            .where(order.tableId.eq(tableId)
                .and(order.orderStatus.eq(OrderStatus.PENDING)))
            .fetch();
    }

    @Override
    public List<OrderResponseVo> findWholeOrderInfos(String tableId) {
        return select(Projections.constructor(OrderResponseVo.class, order.id, dish.id, orderItem.userId,
            dish.name, dish.price, dishCategory.id, dishCategory.name, orderItem.quantity, option.id,
            option.name, choice.id, choice.name, choice.price
        ))
            .from(order)
            .join(orderItem)
            .on(order.eq(orderItem.order))
            .join(orderItemChoice)
            .on(orderItem.eq(orderItemChoice.orderItem))
            .join(choice)
            .on(orderItemChoice.choice.eq(choice))
            .join(option)
            .on(choice.option.eq(option))
            .join(dish)
            .on(orderItem.dish.eq(dish))
            .join(dishCategory)
            .on(dish.dishCategory.eq(dishCategory))
            .where(order.tableId.eq(tableId)
                .and(order.orderStatus.eq(OrderStatus.PENDING)))
            .fetch()
            ;
    }

    @Override
    public List<ParentOrder> findOrdersByPayment(Payment curPayment) {

        return select(order)
            .from(order)
            .join(orderPayment.order, order)
            .join(orderPayment.payment, payment)
            .where(payment.eq(curPayment))
            .fetch();
    }
}
