package com.example.backend.owner.dto;

import com.example.backend.dish.dto.ChoiceDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

public record OwnerResponse() {

    @Schema(name = "옵션 전체 조회 결과", description = "전체 옵션 조회 api의 응답 결과를 담는 dto")
    @Builder
    public record WholeOptionResponseDto(
        List<OptionResponseDto> options
    ) {

    }

    @Schema(name = "옵션 조회 결과", description = "개별 옵션 조회 api의 응답 결과를 담는 dto")
    @Builder
    public record OptionResponseDto(
        Integer optionId,
        String optionName,
        List<ChoiceDto> choices,
        Integer maxChoiceCount,
        Boolean isMandatory
    ) {

    }
}
