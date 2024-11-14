package com.example.backend.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Schema(name = "유저 장바구니 데이터",description = "유저가 장바구니에 담은 정보를 표현하는 DTO")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class UserCartItemDto {
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String userId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	CartDto item;
}
