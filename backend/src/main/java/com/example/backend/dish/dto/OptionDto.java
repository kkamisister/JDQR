package com.example.backend.dish.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.example.backend.dish.entity.Choice;
import com.example.backend.dish.entity.Option;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OptionDto {

	private Integer optionId;
	private String optionName;
	private List<ChoiceDto> choices;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		OptionDto optionDto = (OptionDto)o;
		return Objects.equals(optionId, optionDto.optionId) && Objects.equals(choices, optionDto.choices);
	}

	@Override
	public int hashCode() {
		return Objects.hash(optionId, choices);
	}

	public static OptionDto of(Option option,List<Choice> choices) {

		List<ChoiceDto> choiceDtos = choices.stream().map(ChoiceDto::from).toList();

		return OptionDto.builder()
			.optionId(option.getId())
			.optionName(option.getName())
			.choices(choiceDtos)
			.build();
	}
	public static OptionDto of(Option option) {

		return OptionDto.builder()
			.optionId(option.getId())
			.optionName(option.getName())
			.choices(Collections.emptyList())
			.build();
	}

}
