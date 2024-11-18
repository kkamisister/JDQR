package com.example.backend.table.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.backend.dish.dto.DishResponse;
import com.example.backend.dish.dto.DishResponse.DishDetailInfo;
import com.example.backend.table.entity.Table;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record TableResponse() {

	@Schema(name = "QR정보",
		description = "재생성된 QR정보를 담는다",
		example = "https://jdqr608.duckdns.org/api/v1/order/auth/6721aa9b0d22a923091eef73/eee93bc1-66be-43e2-8075-e06ce2f4ebc6"
	)
	public record QRInfo(
		String url,
		LocalDateTime qrlastUpdatedAt
	){

	}

	@Schema(name = "테이블 이름 정보",description = "테이블 이름정보를 담은 DTO")
	public record TableNameDto(
		String tableName
	){

	}
	@Schema(name = "테이블 정보",description = "전체 테이블의 정보를 담은 DTO")
	public record TableResultDto(
		@ArraySchema(schema = @Schema(implementation = TableDetailInfo.class))
		List<TableDetailInfo> tables,
		@Schema(description = "남은 좌석 수 입니다.")
		int leftSeatNum
	){

	}

	@Schema(name = "테이블 상세정보",description = "테이블 상세정보를 담은 DTO")
	@Builder
	public record TableDetailInfo(
		@Schema(description = "테이블 ID 입니다.")
		String tableId,
		@Schema(description = "테이블 이름 입니다.")
		String name,
		@Schema(description = "테이블의 색상 입니다.")
		String color,
		@Schema(description = "테이블의 QR Link 입니다.")
		String qrLink,
		@Schema(description = "테이블 QR의 마지막 업데이트 일자입니다.")
		@JsonInclude(JsonInclude.Include.NON_NULL)
		LocalDateTime qrlastUpdatedAt,
		@Schema(description = "테이블 좌석 수 입니다.")
		int people,
		@ArraySchema(schema = @Schema(implementation = DishDetailInfo.class))
		List<DishDetailInfo> dishes,
		@Schema(description = "주문한 음식의 총 가격입니다.")
		@JsonInclude(JsonInclude.Include.NON_NULL)
		Integer totalPrice
	){
		public static TableDetailInfo of(Table table,List<DishDetailInfo> dishes){
			return TableDetailInfo.builder()
				.tableId(table.getId())
				.name(table.getName())
				.color(table.getColor())
				.qrLink(table.getQrCode())
				.qrlastUpdatedAt(table.getQrUpdatedAt())
				.people(table.getPeople())
				.dishes(dishes)
				.build();
		}

		public static TableDetailInfo of(Table table,List<DishDetailInfo> dishes,int totalPrice){
			return TableDetailInfo.builder()
				.tableId(table.getId())
				.name(table.getName())
				.color(table.getColor())
				.qrLink(table.getQrCode())
				.qrlastUpdatedAt(table.getQrUpdatedAt())
				.people(table.getPeople())
				.dishes(dishes)
				.totalPrice(totalPrice)
				.build();
		}
	}

}
