package com.example.backend.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderResponseVo {
    private Integer orderId;
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
    private String choicePrice;
}
