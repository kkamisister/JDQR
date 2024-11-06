package com.example.backend.order.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

public record OrderResponse() {

    @Schema(name = "주문 내역 조회 결과 반환 dto", description = "주문 내역 조회 api의 응답 결과를 담는다")
    @Builder
    public record OrderInfoResponseDto(
        Integer orderId,
        String tableName,
        Integer dishCnt,
        Integer price,
        List<OrderDetailResponseDto> orders
    ) {

    }

    @Schema(name = "주문 내역 중 메뉴에 대한 정보를 담고 있는 dto", description = "주문한 메뉴들에 대한 세부 정보를 담는다")
    public record OrderDetailResponseDto(
        Integer dishId,
        String userId,
        String dishName,
        Integer dishCategoryId,
        String dishCategoryName,
        Integer dishPrice,
        List<OptionDto> options,
        Integer quantity
    ) {

    }


}
