package com.example.backend.common.client.toss.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TossErrorResponse {
  private String code;
  private String message;
}
