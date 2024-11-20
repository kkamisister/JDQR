package com.example.backend.order.repository;


import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.QChoice;
import com.example.backend.dish.entity.QOption;
import com.example.backend.order.dto.OrderResponseVo;
import com.example.backend.order.entity.Order;
import com.example.backend.order.entity.ParentOrder;
import com.example.backend.order.entity.OrderItem;
import com.example.backend.order.entity.QOrderItem;
import com.example.backend.order.entity.QOrderItemChoice;
import com.example.backend.order.entity.QPayment;
import com.example.backend.order.enums.PaymentStatus;
import com.example.backend.table.dto.TableOrderResponseVo;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;

import java.util.Collection;
import java.util.List;

import static com.example.backend.dish.entity.QChoice.*;
import static com.example.backend.dish.entity.QDish.*;
import static com.example.backend.dish.entity.QOption.*;
import static com.example.backend.order.entity.QOrder.*;
import static com.example.backend.order.entity.QOrderItem.orderItem;
import static com.example.backend.order.entity.QOrderItemChoice.*;
import static com.example.backend.order.entity.QPayment.*;
import static com.example.backend.order.enums.PaymentStatus.*;

public class OrderItemRepositoryCustomImpl extends Querydsl4RepositorySupport implements OrderItemRepositoryCustom {
    // Order에 해당하는 OrderItem 목록을 반환
    @Override
    public List<OrderItem> findOrderItemByOrder(List<Order> orders) {

        return selectFrom(orderItem)
            .where(orderItem.order.in(orders))
            .fetch();
    }

    @Override
    public List<OrderItem> findOrderItemByParentOrder(ParentOrder parentOrder) {
        return selectFrom(orderItem)
            .join(orderItem.dish, dish)
            .join(orderItem.order, order).fetchJoin()
            .where(order.parentOrder.eq(parentOrder))
            .orderBy(dish.id.asc())
            .fetch();
    }

    // @Override
    // public List<TableOrderResponseVo> findDishOptionsAndChoicesByOrderItem(OrderItem orderItem) {
    //
    //     return select(Projections.constructor(TableOrderResponseVo.class,
    //         QOrderItem.orderItem.id,
    //         dish.id, dish.name, dish.price,
    //         option.id, option.name,
    //         choice.id, choice.name, choice.price
    //     ))
    //         .from(QOrderItem.orderItem)
    //         .join(QOrderItem.orderItem.dish, dish)
    //         .leftJoin(QOrderItem.orderItem.orderItemChoices,orderItemChoice)
    //         .leftJoin(orderItemChoice.choice, choice)
    //         .leftJoin(choice.option, option)
    //         .where(QOrderItem.orderItem.eq(orderItem))
    //         .fetch();
    // }

    public List<TableOrderResponseVo> findAllDishOptionsAndChoicesByParentOrder(ParentOrder parentOrder) {
        return select(Projections.constructor(TableOrderResponseVo.class,
            orderItem.id.as("orderItemId"),
            dish.id.as("dishId"),
            dish.name.as("dishName"),
            dish.description.as("description"),
            dish.image.as("image"),
            dish.price,
            option.id.as("optionId"),
            option.name.as("optionName"),
            choice.id.as("choiceId"),
            choice.name.as("choiceName"),
            choice.price.as("choicePrice")
        ))
            .from(orderItem)
            .join(orderItem.dish, dish)
            .join(orderItem.order.parentOrder.payments, payment)
            .leftJoin(orderItem.orderItemChoices, orderItemChoice)
            .leftJoin(orderItemChoice.choice, choice)
            .leftJoin(choice.option, option)
            .where(orderItem.order.parentOrder.eq(parentOrder)
                .and(orderItem.paidQuantity.lt(orderItem.quantity)))
            .fetch();
    }

}
