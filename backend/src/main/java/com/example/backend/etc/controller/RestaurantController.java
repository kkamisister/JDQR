package com.example.backend.etc.controller;

import static com.example.backend.etc.dto.RestaurantResponse.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.common.dto.CommonResponse.ResponseWithData;
import com.example.backend.common.dto.CommonResponse.ResponseWithMessage;
import com.example.backend.etc.dto.RestaurantProfileDto;
import com.example.backend.etc.dto.RestaurantResponse;
import com.example.backend.etc.dto.RestaurantResponse.RestaurantInfo;
import com.example.backend.etc.service.RestaurantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/map")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "가맹점 API", description = "가맹점 관련 Controller입니다")
public class RestaurantController {

	private final RestaurantService restaurantService;

	@Operation(summary = "가맹점 검색", description = "유저의 화면범위에 있는 가맹점을 검색하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 완료"),
	})
	@GetMapping("/restaurant/keyword")
	public ResponseEntity<ResponseWithData<RestaurantInfo>> restaurantSearchByKeyword(
		@RequestParam("keyword") String keyword,
		@RequestParam(value = "minLat",defaultValue = "33") @Min(value = 33, message = "대한민국을 벗어날 수 없습니다") double minLat,
		@RequestParam(value = "maxLat",defaultValue = "43") @Max(value = 43, message = "대한민국을 벗어날 수 없습니다") double maxLat,
		@RequestParam(value = "minLng",defaultValue = "124") @Min(value = 124, message = "대한민국을 벗어날 수 없습니다") double minLng,
		@RequestParam(value = "maxLng",defaultValue = "132") @Max(value = 132, message = "대한민국을 벗어날 수 없습니다") double maxLng,
		@RequestParam(value = "people",required = false, defaultValue = "0") int people,
		@RequestParam(value = "together",required = false, defaultValue = "false") boolean together){

		RestaurantInfo restaurant = restaurantService.searchByKeyword(keyword, minLat, maxLat, minLng, maxLng,
			people, together);

		ResponseWithData<RestaurantInfo> responseWithData = new ResponseWithData<>(HttpStatus.OK.value(),
			"가맹점 검색에 성공하였습니다.", restaurant);

		return ResponseEntity.status(responseWithData.status())
			.body(responseWithData);
	}


	@Operation(summary = "가맹점 위치 조회", description = "유저의 화면범위에 있는 가맹점을 조회하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 완료"),
	})
	@GetMapping("/restaurant")
	public ResponseEntity<ResponseWithData<RestaurantInfo>> getNearRestaurant(
		@RequestParam(value = "minLat",defaultValue = "33") @Min(value = 33, message = "대한민국을 벗어날 수 없습니다") double minLat,
		@RequestParam(value = "maxLat",defaultValue = "43") @Max(value = 43, message = "대한민국을 벗어날 수 없습니다") double maxLat,
		@RequestParam(value = "minLng",defaultValue = "124") @Min(value = 124, message = "대한민국을 벗어날 수 없습니다") double minLng,
		@RequestParam(value = "maxLng",defaultValue = "132") @Max(value = 132, message = "대한민국을 벗어날 수 없습니다") double maxLng,
		@RequestParam(value = "people",required = false, defaultValue = "0") int people,
		@RequestParam(value = "together",required = false, defaultValue = "false") boolean together){

		RestaurantInfo restaurant = restaurantService.getNearRestaurant(minLat, maxLat, minLng, maxLng,
			people, together);

		ResponseWithData<RestaurantInfo> responseWithData = new ResponseWithData<>(HttpStatus.OK.value(),
			"가맹점 조회에 성공하였습니다.", restaurant);

		return ResponseEntity.status(responseWithData.status())
			.body(responseWithData);
	}


	@Operation(summary = "가맹점 상세 조회", description = "가맹점의 상세정보를 조회하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 완료"),
	})
	@GetMapping("/restaurant/{restaurantId}")
	public ResponseEntity<ResponseWithData<RestaurantDetailInfo>> getRestaurantDetail(
		@PathVariable("restaurantId") @NotNull(message = "아이디는 NULL 일 수 없습니다") Integer restaurantId){

		RestaurantDetailInfo restaurantDetail = restaurantService.getRestaurantDetail(restaurantId);

		ResponseWithData<RestaurantDetailInfo> responseWithData = new ResponseWithData<>(HttpStatus.OK.value(),
			"식당 상세정보 조회에 성공하였습니다", restaurantDetail);

		return ResponseEntity.status(responseWithData.status())
			.body(responseWithData);
	}
}
