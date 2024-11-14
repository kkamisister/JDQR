package com.example.backend.order.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

public record OrderResponse() {

    @Schema(name = "전체 주문 내역 조회 결과 반환 dto", description = "주문 내역 조회 api의 응답 결과를 담는다")
    @Builder
    public record TotalOrderInfoResponseDto(
        String tableName,
        Integer dishCnt,
        Integer price,
        List<OrderInfoResponseDto> orders
    ) {

    }

    @Schema(name = "주문 내역 조회 결과 반환 dto", description = "각 주문별 주문 내역 조회 정보를 담는다")
    @Builder
    public record OrderInfoResponseDto(
        Integer orderId,
        Integer price,
        Integer dishCnt,
        List<DishInfoResponseDto> dishes
    ) {

    }

    @Schema(name = "주문 내역 중 메뉴에 대한 정보를 담고 있는 dto", description = "주문한 메뉴들에 대한 세부 정보를 담는다")
    @Builder
    public record DishInfoResponseDto(
        Integer dishId,
        String userId,
        String dishName,
        Integer dishCategoryId,
        String dishCategoryName,
        Integer price,
        Integer totalPrice,
        List<OptionDetailDto> options,
        Integer quantity
    ) {

    }


}
