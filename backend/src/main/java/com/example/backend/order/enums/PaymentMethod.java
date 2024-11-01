package com.example.backend.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
    UNDEFINED("결제 방식이 정해지지 않은 상태"),
    MONEY_DIVIDE("금액별 1/N 결제 방식"),
    MENU_DIVIDE("메뉴별 결제 방식")
    ;
    private final String explain;
}
