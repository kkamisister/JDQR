package com.example.backend.order.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentResponseVo {
    private Integer parentOrderId;
    private Integer paymentId;
    private Integer paymentDetailId;
    private Integer orderItemId;
    private Integer quantity;
}
