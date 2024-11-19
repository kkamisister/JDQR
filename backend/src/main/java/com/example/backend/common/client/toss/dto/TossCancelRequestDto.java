package com.example.backend.common.client.toss.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TossCancelRequestDto {
  private String cancelReason;
}
