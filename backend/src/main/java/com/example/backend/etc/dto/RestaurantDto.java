package com.example.backend.etc.dto;

import java.util.List;

import com.example.backend.etc.entity.Restaurant;
import com.example.backend.etc.entity.RestaurantCategory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Schema(name = "식당 데이터",description = "식당 정보 데이터")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class RestaurantDto {

	private int id;
	private String restaurantName;
	private List<RestaurantCategoryDto> restaurantCategories;
	private int restTableNum;
	private int restSeatNum;
	private int maxPeopleNum;
	private String address;
	private String image;
	private double lat;
	private double lng;
	private boolean open;

	public static RestaurantDto from(Restaurant restaurant) {
		return RestaurantDto.builder()
			.open(restaurant.getOpen())
			.build();
	}
}
