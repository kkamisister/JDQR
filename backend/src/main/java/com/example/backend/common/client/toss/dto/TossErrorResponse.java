package com.example.backend.common.client.toss.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TossErrorResponse {
  private String code;
  private String message;
}
