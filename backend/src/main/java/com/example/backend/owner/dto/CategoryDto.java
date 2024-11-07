package com.example.backend.owner.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CategoryDto {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer dishCategoryId;
	private String dishCategoryName;

}
