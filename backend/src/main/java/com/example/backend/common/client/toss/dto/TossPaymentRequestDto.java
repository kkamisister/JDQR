package com.example.backend.common.client.toss.dto;

import com.example.backend.order.dto.OrderRequest.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TossPaymentRequestDto {
  private String paymentKey;
  private Integer amount;
  private String orderId;

  public static TossPaymentRequestDto from(PaymentRequestDto paymentRequestDto, String orderId) {
    return TossPaymentRequestDto.builder()
      .paymentKey(paymentRequestDto.paymentKey())
      .amount(paymentRequestDto.amount())
      .orderId(orderId)
      .build();
  }
}
