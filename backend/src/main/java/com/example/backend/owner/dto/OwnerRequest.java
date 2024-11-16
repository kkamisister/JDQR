package com.example.backend.owner.dto;

import java.util.List;

import com.example.backend.dish.dto.ChoiceDto;

import io.swagger.v3.oas.annotations.media.Schema;

public record OwnerRequest() {

	@Schema(name = "옵션 생성 DTO",description = "옵션 생성을 위한 데이터를 담은 DTO")
	public record OptionRequestDto(

		String optionName,
		List<ChoiceDto> choices,
		Integer maxChoiceCount,
		Boolean isMandatory

	){

	}

	@Schema(name = "영업 여부 DTO",description = "영업 전환 정보를 담은 DTO")
	public record BusinessInfo(
		Boolean open
	){

	}
}
