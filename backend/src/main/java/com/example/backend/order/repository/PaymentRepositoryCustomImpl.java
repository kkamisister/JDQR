package com.example.backend.order.repository;

import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.order.entity.ParentOrder;
import com.example.backend.order.entity.Payment;
import com.example.backend.order.entity.QParentOrder;

import java.util.List;

import static com.example.backend.order.entity.QPayment.payment;
import static com.example.backend.order.entity.QParentOrder.parentOrder;
import static com.example.backend.order.entity.QOrder.order;

public class PaymentRepositoryCustomImpl extends Querydsl4RepositorySupport implements PaymentRepositoryCustom {

    @Override
    public List<Payment> findPaymentsByOrders(ParentOrder parentOrder) {
        return select(payment).distinct()
            .where(payment.parentOrder.eq(parentOrder))
            .fetch();
    }
}
