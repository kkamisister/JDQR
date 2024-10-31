package com.example.backend.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record OrderRequest() {

    @Schema(name = "결제 요청 dto", description = "결제 시도 시 필요한 정보를 담고 있는 request dto")
    @Builder
    public record PaymentRequestDto (
        String paymentKey,
        Integer amount
    ) {

    }
}
