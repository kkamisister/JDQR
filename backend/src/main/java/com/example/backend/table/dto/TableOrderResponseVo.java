package com.example.backend.table.dto;

import java.util.List;

import com.example.backend.dish.dto.OptionDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableOrderResponseVo {

	private Integer dishId;
	private String dishName;
	private int price;
	private Integer optionId;
	private String optionName;
	private Integer choiceId;
	private String choiceName;
	private int choicePrice;
}
