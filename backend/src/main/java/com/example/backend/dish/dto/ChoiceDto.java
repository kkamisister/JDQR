package com.example.backend.dish.dto;

import java.util.Objects;

import com.example.backend.dish.entity.Choice;
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
public class ChoiceDto {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer choiceId;
	private String choiceName;
	private int price;

	public static ChoiceDto from(Choice choice){
		return ChoiceDto.builder()
			.choiceId(choice.getId())
			.choiceName(choice.getName())
			.price(choice.getPrice())
			.build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ChoiceDto choiceDto = (ChoiceDto)o;
		return price == choiceDto.price && Objects.equals(choiceId, choiceDto.choiceId)
			&& Objects.equals(choiceName, choiceDto.choiceName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(choiceId, choiceName, price);
	}
}
