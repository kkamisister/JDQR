package com.example.backend.order.repository;

import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.order.dto.OrderResponseVo;
import com.example.backend.order.dto.PaymentResponseVo;
import com.example.backend.order.entity.ParentOrder;
import com.example.backend.order.entity.Payment;
import com.example.backend.order.entity.QParentOrder;
import com.example.backend.order.enums.OrderStatus;
import com.querydsl.core.types.Projections;

import java.util.List;

import static com.example.backend.order.entity.QParentOrder.parentOrder;
import static com.example.backend.order.entity.QOrder.order;
import static com.example.backend.order.entity.QOrderItem.orderItem;
import static com.example.backend.order.entity.QOrderItemChoice.orderItemChoice;
import static com.example.backend.order.entity.QPayment.payment;
import static com.example.backend.order.entity.QPaymentDetail.paymentDetail;
import static com.example.backend.dish.entity.QChoice.choice;
import static com.example.backend.dish.entity.QOption.option;
import static com.example.backend.dish.entity.QDish.dish;
import static com.example.backend.dish.entity.QDishCategory.dishCategory;

public class OrderRepositoryCustomImpl extends Querydsl4RepositorySupport implements OrderRepositoryCustom {
    @Override
    public List<OrderResponseVo> findWholeOrderInfos(ParentOrder parentOrder) {
        return select(Projections.constructor(OrderResponseVo.class, order.id, orderItem.id, dish.id, orderItem.userId,
            dish.name, dish.price, dishCategory.id, dishCategory.name, orderItem.quantity, option.id,
            option.name, choice.id, choice.name, choice.price
        ))
            .from(order)
            .leftJoin(orderItem).on(order.eq(orderItem.order))
            .leftJoin(orderItemChoice).on(orderItem.eq(orderItemChoice.orderItem))
            .leftJoin(choice).on(orderItemChoice.choice.eq(choice))
            .leftJoin(option).on(choice.option.eq(option))
            .join(dish).on(orderItem.dish.eq(dish))
            .join(dishCategory).on(dish.dishCategory.eq(dishCategory))
            .where(order.parentOrder.eq(parentOrder))
            .fetch()
            ;
    }

    @Override
    public List<PaymentResponseVo> findWholePaymentInfos(ParentOrder parentOrder) {
        return select(Projections.constructor(PaymentResponseVo.class, QParentOrder.parentOrder.id, payment.id, paymentDetail.id, paymentDetail.orderItem.id, paymentDetail.quantity))
            .from(QParentOrder.parentOrder)
            .join(payment).on(payment.parentOrder.eq(QParentOrder.parentOrder))
            .join(paymentDetail).on(paymentDetail.payment.eq(payment))
            .fetch()
            ;
    }


}
