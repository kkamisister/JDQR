package com.example.backend.table.dto;

import java.util.List;

import com.example.backend.dish.dto.DishResponse;
import com.example.backend.dish.dto.DishResponse.DishDetailInfo;
import com.example.backend.table.entity.Table;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record TableResponse() {

	@Schema(name = "QR정보",description = "재생성된 QR정보를 담는다")
	public record QRInfo(
		String url
	){

	}

	@Schema(name = "테이블 정보",description = "전체 테이블의 정보를 담은 DTO")
	public record TableResultDto(
		List<TableDetailInfo> tables,
		int leftSeatNum
	){

	}

	@Schema(name = "테이블 상세정보",description = "테이블 상세정보를 담은 DTO")
	@Builder
	public record TableDetailInfo(
		String tableId,
		String name,
		String color,
		String qrLink,
		int people,
		List<DishDetailInfo> dishes,
		@JsonInclude(JsonInclude.Include.NON_NULL)
		Integer totalPrice
	){
		public static TableDetailInfo of(Table table,List<DishDetailInfo> dishes){
			return TableDetailInfo.builder()
				.tableId(table.getId())
				.name(table.getName())
				.color(table.getColor())
				.qrLink(table.getQrCode())
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
				.people(table.getPeople())
				.dishes(dishes)
				.totalPrice(totalPrice)
				.build();
		}
	}

}
