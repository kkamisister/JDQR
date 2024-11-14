package com.example.backend.order.repository;

import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.order.entity.ParentOrder;
import com.example.backend.order.entity.Payment;
import java.util.List;

import static com.example.backend.order.entity.QPayment.payment;
import static com.example.backend.order.entity.QOrderPayment.orderPayment;
import static com.example.backend.order.entity.QOrder.order;

public class PaymentRepositoryCustomImpl extends Querydsl4RepositorySupport implements PaymentRepositoryCustom {

    @Override
    public List<Payment> findPaymentsByOrders(List<ParentOrder> parentOrders) {
        return select(payment).distinct()
            .join(orderPayment.payment, payment)
            .join(orderPayment.order, order)
            .where(order.in(parentOrders))
            .fetch();
    }
}
