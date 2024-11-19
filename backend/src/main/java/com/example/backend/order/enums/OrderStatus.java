package com.example.backend.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PENDING("주문 중", OrderStatusGroup.UNFINISHED),
    PAY_WAITING("결제 대기중", OrderStatusGroup.UNFINISHED),
    PAID("결제완료", OrderStatusGroup.FINISHED),
    CANCELLED("주문 취소", OrderStatusGroup.FINISHED),;

    private final String explain;
    private final OrderStatusGroup group;
}