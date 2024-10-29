package com.example.backend.table.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record TableRequest(){

	@Schema(name = "테이블 생성 데이터",description = "테이블 생성 시 필요한 데이터")
	@Builder
	public record TableInfo(
		@Schema(name = "테이블 이름", description = "점주가 설정한 테이블 이름")
		String name,
		@Schema(name = "테이블 색상",description = "점주가 설정한 테이블 색상")
		String color,
		@Schema(name = "인원 수",description = "점주가 설정한 테이블 인원 수")
		int people,
		@Schema(name = "좌표정보",description = "테이블의 좌표를 담은 리스트")
		List<Grid> position
	){

	}
}
