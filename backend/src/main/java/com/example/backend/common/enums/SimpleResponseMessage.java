package com.example.backend.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ResponseWithMessage 형태로 반환하여야 할 컨트롤러가 있는 경우, 이거를 사용
 */
@Getter
@RequiredArgsConstructor
public enum SimpleResponseMessage {
    ORDER_ITEM_EMPTY("아직 담은 상품이 없습니다."),

    ORDER_SUCCESS("주문에 성공하였습니다.");

    private final String message;
}
