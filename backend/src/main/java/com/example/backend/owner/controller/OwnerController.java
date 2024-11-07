package com.example.backend.owner.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.common.dto.CommonResponse;
import com.example.backend.common.dto.CommonResponse.ResponseWithData;
import com.example.backend.dish.dto.DishRequest;
import com.example.backend.dish.dto.DishResponse;
import com.example.backend.dish.dto.DishResponse.DishSummaryResultDto;
import com.example.backend.dish.service.DishService;
import com.example.backend.etc.dto.RestaurantProfileDto;
import com.example.backend.etc.service.RestaurantService;
import com.example.backend.owner.service.OwnerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/owner")
@Tag(name = "점주 설정 관련 API", description = "점주의 설정 관련 Controller입니다")
public class OwnerController {

	private final OwnerService ownerService;
	private final RestaurantService restaurantService;

	//1. 전체 메뉴 조회
	@Operation(summary = "전체 메뉴 조회", description = "전체 메뉴를 조회하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메뉴 조회 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@GetMapping("/dish")
	public ResponseEntity<ResponseWithData<DishSummaryResultDto>> getAllMenus(HttpServletRequest request){

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		DishSummaryResultDto allMenus = ownerService.getAllMenus(userId);

		ResponseWithData<DishSummaryResultDto> responseWithData = new ResponseWithData<>(HttpStatus.OK.value(),
			"전체메뉴 조회에 성공하였습니다.",allMenus);

		return ResponseEntity.status(responseWithData.status())
			.body(responseWithData);
	}

	@Operation(summary = "메뉴 상세 조회", description = "상세메뉴를 조회하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메뉴 조회 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@GetMapping("/dish/{dishId}")
	public ResponseEntity<ResponseWithData<DishSummaryResultDto>> getMenus(HttpServletRequest request, @PathVariable("dishId") Integer dishId) {

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		DishSummaryResultDto menu = ownerService.getMenu(userId, dishId);

		ResponseWithData<DishSummaryResultDto> responseWithData = new ResponseWithData<>(HttpStatus.OK.value(),
			"상세메뉴 조회에 성공하였습니다.",menu);

		return ResponseEntity.status(responseWithData.status())
			.body(responseWithData);

	}

	//2. 메뉴 추가
	@Operation(summary = "메뉴 추가", description = "새로운 메뉴를 추가하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메뉴 추가 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@PostMapping("")
	public ResponseEntity<CommonResponse.ResponseWithMessage> addDish(@RequestBody DishRequest.DishInfo dishInfo,
		HttpServletRequest request){
		//2-1. 유저 확인
		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		//2-2. db에 변경사항 저장
		CommonResponse.ResponseWithMessage responseWithMessage = ownerService.addDish(userId, dishInfo);

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

	//3. 메뉴 삭제
	@Operation(summary = "메뉴 삭제", description = "기존 메뉴를 삭제하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메뉴 삭제 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@DeleteMapping("")
	public ResponseEntity<CommonResponse.ResponseWithMessage> deleteDish(@RequestParam("dishId") @Parameter(description = "메뉴ID", required = true) Integer dishId,
		HttpServletRequest request){
		//3-1. 유저 확인
		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		//3-2. db에 변경사항 저장
		CommonResponse.ResponseWithMessage responseWithMessage = ownerService.removeDish(userId, dishId);

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

	//4. 메뉴 수정
	@Operation(summary = "메뉴 수정", description = "기존 메뉴를 수정하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메뉴 수정 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@PutMapping("")
	public ResponseEntity<CommonResponse.ResponseWithMessage> updateDish(@RequestParam("dishId") @Parameter(description = "메뉴ID", required = true) Integer dishId, @RequestBody DishRequest.DishInfo dishInfo,
		HttpServletRequest request){
		//4-1. 유저 확인
		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		//4-2. db에 변경사항 저장
		CommonResponse.ResponseWithMessage responseWithMessage = ownerService.updateDish(userId, dishId, dishInfo);

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

	//5. 메뉴 검색



	//6. 옵션 추가


	//7.
	@Operation(summary = "사업장 조회", description = "사업장을 조회하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 완료"),
	})
	@GetMapping("/restaurant/{restaurantId}")
	public ResponseEntity<ResponseWithData<RestaurantProfileDto>> getRestaurant(@PathVariable("restaurantId") Integer restaurantId,
		HttpServletRequest request) {

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		RestaurantProfileDto restaurant = restaurantService.getRestaurant(restaurantId, userId);

		ResponseWithData<RestaurantProfileDto> responseWithData = new ResponseWithData<>(
			HttpStatus.OK.value(), "사업장 조회에 성공하였습니다.", restaurant);

		return ResponseEntity.status(responseWithData.status())
			.body(responseWithData);
	}


	@Operation(summary = "사업장 등록", description = "사업장을 등록하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 완료"),
	})
	@PostMapping("/restaurant")
	public ResponseEntity<CommonResponse.ResponseWithMessage> createRestaurant(@RequestBody RestaurantProfileDto restaurantProfile,
		HttpServletRequest request) {

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		restaurantService.createRestaurant(restaurantProfile,userId);

		CommonResponse.ResponseWithMessage responseWithMessage = new CommonResponse.ResponseWithMessage(HttpStatus.OK.value(),
			"사업장 정보 설정에 성공하였습니다");

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

}
