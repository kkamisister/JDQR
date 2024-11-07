package com.example.backend.owner.dto;

import java.util.List;

public record OwnerResponse(

) {
	public record CategoryResult(
		List<CategoryDto> dishCategories
	){

	}

}
