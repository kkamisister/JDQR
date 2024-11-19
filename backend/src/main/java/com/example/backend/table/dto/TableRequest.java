package com.example.backend.table.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record TableRequest(){

	@Schema(name = "테이블 생성 데이터",description = "테이블 생성 시 필요한 데이터")
	@Builder
	public record TableInfo(
		@NotBlank(message = "테이블 이름은 비어있을 수 없습니다.")
		@Length(max = 20)
		@Schema(name = "테이블 이름",
			description = "점주가 설정한 테이블 이름",
			example = "영표의 책상"
		)
		String name,
		@Schema(name = "테이블 색상",
			description = "점주가 설정한 테이블 색상",
			example = "#ffffff"
		)
		String color,
		@NotNull(message = "인원은 비어있을 수 없습니다.")
		@Schema(name = "인원 수",
			description = "점주가 설정한 테이블 인원 수",
			example = "6"
		)
		Integer people,
		@Schema(name = "테이블 ID",
			description = "테이블의 ID",
			example = "6721aa9b0d22a923091eef73"
		)
		String tableId
	){

	}
}
