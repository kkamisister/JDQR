package com.example.backend.dish.dto;

import com.example.backend.dish.entity.Choice;

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
public class ChoiceDto {

	private int choiceId;
	private String choiceName;
	private int price;

	public static ChoiceDto from(Choice choice){
		return ChoiceDto.builder()
			.choiceId(choice.getId())
			.choiceName(choice.getName())
			.price(choice.getPrice())
			.build();
	}
}
