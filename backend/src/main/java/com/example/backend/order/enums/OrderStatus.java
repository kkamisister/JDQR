package com.example.backend.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PENDING("결제 대기중"),
    PAID("결제완료"),
    CANCELLED("주문 취소");

    private final String explain;
}