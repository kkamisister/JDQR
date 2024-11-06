package com.example.backend.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class OptionDto {
	private Integer optionId;
	private String optionName;
	private Integer choiceId;
	private String choiceName;
	private Integer choicePrice;
}
