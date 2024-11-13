package com.example.backend.order.dto;

import com.example.backend.order.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Builder
@Schema(name = "음식 데이터",description = "장바구니에 담은 음식 데이터")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class DummyOrderDto {
	private String tableId;
	private List<DummyDishDto> dummyDishDtos;
	private PaymentMethod paymentMethod;
}
