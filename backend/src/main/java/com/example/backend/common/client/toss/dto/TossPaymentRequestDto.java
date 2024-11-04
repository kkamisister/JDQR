package com.example.backend.common.client.toss.dto;

import com.example.backend.order.dto.CartRequest;
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

  public static TossPaymentRequestDto from(CartRequest.TossPaymentRequestDto tossPaymentRequestDto, String orderId) {
    return com.example.backend.common.client.toss.dto.TossPaymentRequestDto.builder()
      .paymentKey(tossPaymentRequestDto.paymentKey())
      .amount(tossPaymentRequestDto.amount())
      .orderId(orderId)
      .build();
  }
}
