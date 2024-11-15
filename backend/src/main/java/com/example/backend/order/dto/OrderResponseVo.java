package com.example.backend.order.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderResponseVo {
    private Integer orderId;
    private Integer orderItemId;
    private Integer dishId;
    private String userId;
    private String dishName;
    private Integer dishPrice;
    private Integer dishCategoryId;
    private String dishCategoryName;
    private Integer quantity;
    private Integer optionId;
    private String optionName;
    private Integer choiceId;
    private String choiceName;
    private Integer choicePrice;
}
