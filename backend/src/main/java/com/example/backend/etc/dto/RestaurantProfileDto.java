package com.example.backend.etc.dto;

import com.example.backend.etc.entity.Restaurant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

	@NotBlank(message = "식당 이름은 비어있을 수 없습니다.")
	@Schema(
		description = "식당 이름 입니다.",
		example = "영표식당"
	)
	private String restaurantName;
	@NotBlank(message = "식당 주소는 비어있을 수 없습니다.")
	@Schema(
		description = "식당 주소 입니다.",
		example = "경기도 용인시 수지구 디지털밸리로 81(죽전동)"
	)
	private String address;
	@NotBlank(message = "식당 번호는 비어있을 수 없습니다.")
	@Schema(
		description = "식당 번호 입니다.",
		example = "010-1111-1111"
	)
	private String phoneNumber;
	@Schema(
		description = "업태 정보 입니다",
		example = "음식점"
	)
	private String industry;
	@NotBlank(message = "사업자 등록번호는 비어있을 수 없습니다.")
	@Schema(
		description = "사업자 등록번호 입니다.",
		example = "11-11245-47-86686"
	)
	private String registrationNumber;
	@Schema(
		description = "식당 이미지 입니다.",
		example = "https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_22.jfif"
	)
	private String image;
	@Schema(
		description = "위도 정보 입니다.",
		example = "33.2340982390423"
	)
	@NotNull(message = "위도는 비어있을 수 없습니다.")
	@Min(value = 33, message = "대한민국을 벗어날 수 없습니다")
	@Max(value = 43, message = "대한민국을 벗어날 수 없습니다")
	private Double lat;
	@Schema(
		description = "경도 정보 입니다.",
		example = "129.21904182409"
	)
	@NotNull(message = "경도는 비어있을 수 없습니다.")
	@Min(value = 124, message = "대한민국을 벗어날 수 없습니다")
	@Max(value = 132, message = "대한민국을 벗어날 수 없습니다")
	private Double lng;

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
