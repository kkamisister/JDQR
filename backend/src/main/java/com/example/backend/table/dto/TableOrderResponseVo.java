package com.example.backend.table.dto;

import java.util.List;

import com.example.backend.dish.dto.OptionDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TableOrderResponseVo {
	private Integer orderItemId;
	private Integer dishId;
	private String dishName;
	private String description;
	private String image;

	private int price;
	private Integer optionId;
	private String optionName;
	private Integer choiceId;
	private String choiceName;
	private int choicePrice;
}
