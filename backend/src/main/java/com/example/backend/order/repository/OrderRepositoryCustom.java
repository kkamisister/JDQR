package com.example.backend.order.repository;

import com.example.backend.order.dto.OrderResponseVo;
import com.example.backend.order.dto.PaymentResponseVo;
import com.example.backend.order.entity.ParentOrder;
import com.example.backend.order.entity.Payment;

import java.util.List;

public interface OrderRepositoryCustom {
    List<OrderResponseVo> findWholeOrderInfos(ParentOrder parentOrder);


    List<PaymentResponseVo> findWholePaymentInfos(ParentOrder parentOrder);
}
