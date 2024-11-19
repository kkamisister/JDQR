package com.example.backend.order.dto;

import com.example.backend.order.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

public record OrderRequest() {

    @Schema(name = "주문 항목 dto", description = "메뉴별 결제 방식으로 결제 시, 선택된 각 주문 항목에 대한 정보를 담고 있는 request dto")
    @Builder
    public record OrderItemRequestDto (
        Integer orderItemId,
        Integer quantity
    ) {

    }
}
