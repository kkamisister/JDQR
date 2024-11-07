package com.example.backend.dish.controller;

import static com.example.backend.common.dto.CommonResponse.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dish.dto.DishResponse;
import com.example.backend.dish.dto.DishResponse.DishDetailInfo;
import com.example.backend.dish.dto.DishResponse.DishSearchResultDto;
import com.example.backend.dish.dto.DishResponse.DishSummaryResultDto;
import com.example.backend.dish.service.DishService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController //body에 데이터를 담아서 보낸다는 의미
@RequiredArgsConstructor
@RequestMapping("/api/v1/dish")
@Slf4j
@Tag(name = "손님 메뉴관련 API", description = "손님의 메뉴관련 Controller입니다")
public class DishController {

	private final DishService dishService;

	@Operation(summary = "메뉴판 조회", description = "메뉴판을 조회하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메뉴판 조회 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@GetMapping("")
	public ResponseEntity<ResponseWithData<DishSummaryResultDto>> getAllDishes(HttpServletRequest request){

		String tableId = (String)request.getAttribute("tableId");

		DishSummaryResultDto allDishes = dishService.getAllDishes(tableId);

		ResponseWithData<DishSummaryResultDto> responseWithData = new ResponseWithData<>(HttpStatus.OK.value(),"메뉴판 조회에 성공하였습니다",allDishes);


		return ResponseEntity.status(responseWithData.status())
			.body(responseWithData);
	}

	@Operation(summary = "메뉴상세 조회", description = "메뉴를 상세 조회하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메뉴 조회 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@GetMapping("{dishId}")
	public ResponseEntity<ResponseWithData<DishDetailInfo>> getDish(@PathVariable("dishId") Integer dishId,HttpServletRequest request){

		String tableId = (String)request.getAttribute("tableId");
		DishDetailInfo dish = dishService.getDish(dishId, tableId);

		ResponseWithData<DishDetailInfo> dishDetailInfoResponseWithData = new ResponseWithData<>(HttpStatus.OK.value(),
			"메뉴 상세조회에 성공하였습니다",dish);

		return ResponseEntity.status(dishDetailInfoResponseWithData.status())
			.body(dishDetailInfoResponseWithData);
	}


	@Operation(summary = "메뉴검색", description = "메뉴를 검색하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메뉴 검색 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@GetMapping("search")
	public ResponseEntity<ResponseWithData<DishSearchResultDto>> searchDish(@RequestParam("keyword") @Parameter(description = "메뉴검색 키워드", required = true) String keyword,
		@RequestParam("restaurantId") @Parameter(description = "메뉴검색 키워드", required = true) Integer restaurantId,
		HttpServletRequest request){

		DishSearchResultDto searchedDishes = dishService.getSearchedDishes(keyword, restaurantId);
		ResponseWithData<DishSearchResultDto> searchedDishListResponseWithData = new ResponseWithData<>(HttpStatus.OK.value(),"메뉴판 조회에 성공하였습니다",searchedDishes);

		return ResponseEntity.status(searchedDishListResponseWithData.status())
			.body(searchedDishListResponseWithData);
	}
}
