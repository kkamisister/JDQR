package com.example.backend.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatusGroup {
    UNFINISHED("아직 끝나지 않은 주문"),
    FINISHED("끝난 주문")
    ;

    private final String explain;
}
