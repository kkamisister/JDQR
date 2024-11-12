package com.example.backend.order.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Schema(name = "음식 데이터",description = "장바구니에 담은 음식 데이터")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class CartDto {
	private Integer dishId;
	private String userId;
	private String dishName;
	private Integer dishCategoryId;
	private String dishCategoryName;
	private List<Integer> choiceIds;
	private int price; // 기존 가격 + choice 가격을 합친 price
	private int quantity;
	private LocalDateTime orderedAt;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CartDto cartDto = (CartDto)o;
		return Objects.equals(dishId, cartDto.dishId) && Objects.equals(userId, cartDto.userId)
			&& Objects.equals(choiceIds, cartDto.choiceIds);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dishId, userId, choiceIds);
	}
}
