package com.example.backend.order.dto;

import java.util.List;

import com.example.backend.order.enums.PaymentMethod;
import com.example.backend.order.dto.OrderRequest.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record CartRequest() {

    @Schema(name = "토스페이먼츠 결제 요청 dto", description = "토스페이먼츠 결제 시도 시 필요한 정보를 담고 있는 request dto")
    @Builder
    public record TossPaymentRequestDto(
        String paymentKey,
        Integer amount
    ) {

    }

    @Schema(name = "결제 요청 dto", description = "N빵 결제 방식 혹은 메뉴별 결제 방식으로 결제하려고 시도할 때, 결제를 위해 필요한 정보를 담고 있는 request dto")
    @Builder
    public record PaymentRequestDto(
        PaymentMethod type,
        Integer peopleNum,
        Integer serveNum,
        List<OrderItemRequestDto> orderItemInfos
    ) {

    }



}
