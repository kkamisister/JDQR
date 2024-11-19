package com.example.backend.common.login.dto;

import com.example.backend.common.dto.AuthToken;
import com.example.backend.etc.entity.Restaurant;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginInfo {

	AuthToken authToken;
	String userName;
	String restaurantName;
	String address;
	String phoneNumber;
	String industry;
	String registrationNumber;
	String image;
	Double lat;
	Double lng;


	public static LoginInfo of(AuthToken authToken,String userName, Restaurant restaurant){
		return LoginInfo.builder()
			.authToken(authToken)
			.userName(userName)
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
