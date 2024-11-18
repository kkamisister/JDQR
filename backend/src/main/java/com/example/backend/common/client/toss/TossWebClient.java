package com.example.backend.common.client.toss;

import com.example.backend.common.client.JDQRWebClient;
import com.example.backend.common.client.toss.dto.TossErrorResponse;
import com.example.backend.common.client.toss.dto.TossPaymentRequestDto;
import com.example.backend.common.client.toss.dto.TossPaymentResponseDto;
import com.example.backend.common.util.JsonUtil;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TossWebClient {
  @Value("${external.url.toss}")
  private String tossPaymentsUrl;
  @Value("${external.token.toss}")
  private String tossPaymentsToken;

  private final JDQRWebClient jdqrWebClient;

  public TossPaymentResponseDto requestPayment(TossPaymentRequestDto tossPaymentRequestDto) {
    Map<String, String> headers = Maps.newHashMap();

    byte[] encodedBytes = Base64.getEncoder().encode((tossPaymentsToken + ":").getBytes());
    String encodedString = new String(encodedBytes);

    headers.put("Content-Type", "application/json");
    headers.put("Authorization", String.format("Basic %s", encodedString));

    try {
      String tossResponse = jdqrWebClient.postFromMap(tossPaymentsUrl + "/v1/payments/confirm",
          tossPaymentRequestDto,
          headers
        )
        .bodyToMono(String.class)
        .block();

      TossPaymentResponseDto response = JsonUtil.read(tossResponse, TossPaymentResponseDto.class);
      response.setSuccess(true);
      return response;
    } catch (Exception e) {

      System.out.println("e.getMessage() = " + e.getMessage());
      
      return TossPaymentResponseDto.builder()
        .success(false)
        .errorMessage(e.getMessage())
        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.name())
        .build();
    }

  }
}
