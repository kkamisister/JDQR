package com.example.backend.dish.controller;

import static com.example.backend.dish.dto.DishResponse.*;
import static com.example.backend.common.dto.CommonResponse.*;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.common.dto.CommonResponse;
import com.example.backend.common.dto.CommonResponse.ResponseWithData;
import com.example.backend.dish.dto.DishRequest;
import com.example.backend.dish.dto.DishRequest.DishInfo;
import com.example.backend.dish.dto.DishResponse;
import com.example.backend.dish.dto.DishResponse.DishSummaryResultDto;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.service.DishService;

import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "메뉴설정 관련 API", description = "메뉴 설정 관련 Controller입니다")
public class DishController {

	private final DishService dishService;

	//1. 전체 메뉴 조회


	//2. 메뉴 추가
	@Operation(summary = "메뉴 추가", description = "새로운 메뉴를 추가하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메뉴 추가 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@PostMapping("")
	public ResponseEntity<ResponseWithMessage> addDish(@RequestBody DishInfo dishInfo,HttpServletRequest request){
		//2-1. 유저 확인
		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		//2-4. db에 저장
		ResponseWithMessage responseWithMessage = dishService.addDish(userId, dishInfo);

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}
	//3. 메뉴 삭제

	//4. 메뉴 수정

	//5. 카테고리 추가

	//6. 옵션 추가



}
