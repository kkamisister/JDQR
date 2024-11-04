package com.example.backend.etc.dto;

import com.example.backend.etc.entity.Restaurant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Schema(name = "사업장 정보 DTO",description = "사업장 정보 데이터")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class RestaurantProfileDto {

	private String restaurantName;
	private String address;
	private String phoneNumber;
	private String industry;
	private String registrationNumber;
	private String image;
	private double lat;
	private double lng;

	public static RestaurantProfileDto from(Restaurant restaurant) {

		return RestaurantProfileDto.builder()
			.restaurantName(restaurant.getName())
			.address(restaurant.getAddress())
			.phoneNumber(restaurant.getPhoneNumber())
			.industry(restaurant.getIndustry())
			.registrationNumber(restaurant.getRegistrationNumber())
			.image(restaurant.getImage())
			.lat(restaurant.getLatitude())
			.lng(restaurant.getLongitude())
			.build();

	}

}
