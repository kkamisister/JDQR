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

    ORDER_SUCCESS("주문에 성공하였습니다."),

    ORDER_STATUS_CHANGED("주문의 상태를 '결제 대기 중'으로 성공적으로 변경하였습니다."),

    // 토스 결제 api 콜백 처리 관련 응답
    PAYMENT_CANCELLED_EXCEED_PURCHASE_AMOUNT("주문한 금액보다 더 많은 양의 결제를 하려고 시도하여 결제가 취소되었습니다."),
    PAYMENT_CANCELLED_EXCEED_MENU_AMOUNT("주문한 메뉴보다 더 많은 양의 메뉴를 결제하려고 시도하여 결제가 취소되었습니다."),
    PAYMENT_BAD_REQUEST("비정상적인 결제 요청입니다."),
    PAYMENT_ALREADY_PAID("이미 결제된 주문입니다."),
    PAYMENT_SUCCESS("주문에 성공하였습니다."),
    WHOLE_PAYMENT_SUCCESS("주문에 대한 모든 결제가 완료되었습니다."),
    PAYMENT_FAILED("주문에 실패하였습니다."),
    ;

    private final String message;
}
