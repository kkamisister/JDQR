package com.example.backend.owner.controller;

import com.example.backend.common.util.JsonUtil;
import com.example.backend.etc.dto.RestaurantResponse.RestaurantBusinessDto;
import com.example.backend.owner.dto.OwnerRequest.OptionRequestDto;
import com.example.backend.owner.dto.OwnerResponse.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.common.dto.CommonResponse.*;
import com.example.backend.dish.dto.DishRequest.*;
import com.example.backend.dish.dto.DishResponse.DishSummaryResultDto;
import com.example.backend.etc.dto.RestaurantProfileDto;
import com.example.backend.etc.service.RestaurantService;
import com.example.backend.owner.dto.CategoryDto;
import com.example.backend.owner.service.OwnerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/owner")
@Tag(name = "점주 설정 관련 API", description = "점주의 설정 관련 Controller입니다")
@Slf4j
public class OwnerController {

	private final OwnerService ownerService;
	private final RestaurantService restaurantService;

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

	@Operation(summary = "메뉴 추가", description = "새로운 메뉴를 추가하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메뉴 추가 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@PostMapping(value = "/dish")
	public ResponseEntity<ResponseWithMessage> addDish(@RequestParam(name = "dishInfo") String json, @RequestParam(name = "imageFile", required = false) MultipartFile imageFile,
		HttpServletRequest request){

		DishInfo dishInfo = JsonUtil.read(json, DishInfo.class);
		//2-1. 유저 확인
		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		//2-2. db에 변경사항 저장
		ResponseWithMessage responseWithMessage = ownerService.addDish(userId, dishInfo, imageFile);

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

	//3. 메뉴 삭제
	@Operation(summary = "메뉴 삭제", description = "기존 메뉴를 삭제하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메뉴 삭제 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@DeleteMapping("/dish")
	public ResponseEntity<ResponseWithMessage> deleteDish(@RequestParam("dishId") @Parameter(description = "메뉴ID", required = true) Integer dishId,
		HttpServletRequest request){
		//3-1. 유저 확인
		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		//3-2. db에 변경사항 저장
		ResponseWithMessage responseWithMessage = ownerService.removeDish(userId, dishId);

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

	//4. 메뉴 수정
	@Operation(summary = "메뉴 수정", description = "기존 메뉴를 수정하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메뉴 수정 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@PutMapping("/dish")
	public ResponseEntity<ResponseWithMessage> updateDish(@RequestParam("dishId") @Parameter(description = "메뉴ID", required = true) String dishIdString,
		@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
		@RequestParam(value = "dishInfo") String json,
		HttpServletRequest request){

		// 4-0. String으로 받은 인수들 변환하기
		Integer dishId = Integer.valueOf(dishIdString);
		DishInfo dishInfo = JsonUtil.read(json, DishInfo.class);
		//4-1. 유저 확인
		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		//4-2. db에 변경사항 저장
		ResponseWithMessage responseWithMessage = ownerService.updateDish(userId, dishId, dishInfo, imageFile);

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

	@Operation(summary = "전체 메뉴 카테고리 조회", description = "전체 메뉴 카테고리를 조회하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "카테고리 조회 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@GetMapping("/dish/category")
	public ResponseEntity<ResponseWithData<CategoryResult>> getAllCategories(HttpServletRequest request){

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		CategoryResult allCategories = ownerService.getAllCategories(userId);

		ResponseWithData<CategoryResult> responseWithData = new ResponseWithData<>(HttpStatus.OK.value(), "전체 메뉴 카테고리 조회에 성공하였습니다.",
			allCategories);

		return ResponseEntity.status(responseWithData.status())
			.body(responseWithData);

	}

	@Operation(summary = "카테고리 추가", description = "카테고리를 추가하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "카테고리 추가 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@PostMapping("/dish/category")
	public ResponseEntity<ResponseWithMessage> createCategory(@RequestBody CategoryDto categoryDto,HttpServletRequest request){

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		ownerService.createCategory(categoryDto,userId);

		ResponseWithMessage responseWithMessage = new ResponseWithMessage(
			HttpStatus.OK.value(), "카테고리 추가에 성공하였습니다."
		);

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

	@Operation(summary = "카테고리 삭제", description = "카테고리를 추가하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "카테고리 추가 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@DeleteMapping("/dish/category")
	public ResponseEntity<ResponseWithMessage> removeCategory(@RequestParam("dishCategoryId") Integer dishCategoryId,HttpServletRequest request){

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		ownerService.removeCategory(dishCategoryId,userId);

		ResponseWithMessage responseWithMessage = new ResponseWithMessage(
			HttpStatus.OK.value(), "카테고리 삭제에 성공하였습니다."
		);

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

	@Operation(summary = "전체 옵션 조회", description = "전체 옵션을 조회하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "옵션 조회 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@GetMapping("/option/all")
	public ResponseEntity<ResponseWithData<WholeOptionResponseDto>> getWholeOptionInfo(HttpServletRequest request){
		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		WholeOptionResponseDto responseDto = ownerService.getWholeOptionInfo(userId);

		ResponseWithData<WholeOptionResponseDto> response = new ResponseWithData<>(HttpStatus.OK.value(), "전체 옵션 조회를 완료하였습니다", responseDto);

		return ResponseEntity.status(response.status()).body(response);
	}

	@Operation(summary = "상세 옵션 조회", description = "상세 옵션을 조회하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "옵션 조회 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@GetMapping("/option/{optionId}")
	public ResponseEntity<ResponseWithData<OptionResponseDto>> getIndividualOptionInfo(HttpServletRequest request,
																							@PathVariable Integer optionId){
		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		OptionResponseDto responseDto = ownerService.getIndividualOptionInfo(userId, optionId);

		ResponseWithData<OptionResponseDto> response = new ResponseWithData<>(HttpStatus.OK.value(), "전체 옵션 조회를 완료하였습니다", responseDto);

		return ResponseEntity.status(response.status()).body(response);
	}

	@Operation(summary = "옵션 추가", description = "옵션을 추가하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "옵션 추가 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@PostMapping("/option")
	public ResponseEntity<ResponseWithMessage> createOption(@RequestBody OptionRequestDto optionRequestDto,HttpServletRequest request){

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		ownerService.createOption(userId,optionRequestDto);

		ResponseWithMessage responseWithMessage = new ResponseWithMessage(HttpStatus.OK.value(),
			"옵션생성에 성공하였습니다.");

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

	@Operation(summary = "옵션 삭제", description = "옵션을 삭제하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "옵션 삭제 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@DeleteMapping("/option")
	public ResponseEntity<ResponseWithMessage> deleteOption(@RequestParam("optionId") Integer optionId,HttpServletRequest request){

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		ownerService.deleteOption(userId,optionId);

		ResponseWithMessage responseWithMessage = new ResponseWithMessage(
			HttpStatus.OK.value(),"옵션 삭제에 성공하였습니다"
		);

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

	//4. 메뉴 수정
	@Operation(summary = "옵션 수정", description = "옵션을 수정하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "옵션 수정 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@PutMapping("/option")
	public ResponseEntity<ResponseWithMessage> updateOption(@RequestParam("optionId") @Parameter(description = "옵션ID", required = true) Integer optionId,
		@RequestBody OptionRequestDto optionDto, HttpServletRequest request){

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		//4-2. db에 변경사항 저장
		ownerService.updateOption(userId, optionId, optionDto);

		ResponseWithMessage responseWithMessage = new ResponseWithMessage(HttpStatus.OK.value(),"옵션 수정에 성공하였습니다");

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

	@Operation(summary = "사업장 조회", description = "사업장을 조회하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 완료"),
	})
	@GetMapping("/restaurant")
	public ResponseEntity<ResponseWithData<RestaurantProfileDto>> getRestaurant(HttpServletRequest request) {

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		RestaurantProfileDto restaurant = restaurantService.getRestaurant(userId);

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
	public ResponseEntity<ResponseWithMessage> createRestaurant(@RequestBody @Valid RestaurantProfileDto restaurantProfile,
		HttpServletRequest request) {

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		restaurantService.createRestaurant(restaurantProfile,userId);

		ResponseWithMessage responseWithMessage = new ResponseWithMessage(HttpStatus.OK.value(),
			"사업장 정보 설정에 성공하였습니다");

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

	@Operation(summary = "영업여부 조회", description = "영업여부를 조회하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 완료"),
	})
	@GetMapping("/restaurant/status")
	public ResponseEntity<ResponseWithData<RestaurantBusinessDto>> getBusinessStatus(HttpServletRequest request){

		// 유저확인
		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		// db에 변경사항 저장
		RestaurantBusinessDto restaurant = restaurantService.getBusinessStatus(userId);

		ResponseWithData<RestaurantBusinessDto> responseWithData = new ResponseWithData<>(
			HttpStatus.OK.value(), "사업장 조회에 성공하였습니다.", restaurant);

		return ResponseEntity.status(responseWithData.status())
			.body(responseWithData);
	}

	@Operation(summary = "영업여부 변경", description = "영업여부를 변경하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "변경 완료"),
	})
	@PutMapping("/restaurant/status")
	public ResponseEntity<ResponseWithMessage> changeBusinessStatus(HttpServletRequest request){

		// 유저확인
		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		// db에 변경사항 저장
		restaurantService.updateBusinessStatus(userId);

		ResponseWithMessage responseWithMessage = new ResponseWithMessage(HttpStatus.OK.value(),
			"영업여부가 변경되었습니다.");

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

}
