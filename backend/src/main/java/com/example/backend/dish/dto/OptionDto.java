package com.example.backend.dish.dto;

import java.util.List;

import com.example.backend.dish.entity.Choice;
import com.example.backend.dish.entity.Option;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OptionDto {

	private int optionId;
	private String optionName;
	private List<ChoiceDto> choices;

	public static OptionDto of(Option option,List<Choice> choices) {

		List<ChoiceDto> choiceDtos = choices.stream().map(ChoiceDto::from).toList();

		return OptionDto.builder()
			.optionId(option.getId())
			.optionName(option.getName())
			.choices(choiceDtos)
			.build();


	}

}
