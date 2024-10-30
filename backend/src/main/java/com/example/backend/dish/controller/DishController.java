package com.example.backend.dish.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.common.dto.CommonResponse;
import com.example.backend.common.dto.CommonResponse.ResponseWithData;
import com.example.backend.dish.dto.DishResponse;
import com.example.backend.dish.dto.DishResponse.DishSummaryResultDto;
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

	@Operation(summary = "모든 메뉴 조회", description = "등록된 모든 메뉴를 조회하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메뉴 조회 성공"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@GetMapping("/all")
	public ResponseEntity<ResponseWithData<DishSummaryResultDto>> getAllDishes(HttpServletRequest request){

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		log.info("User ID: {}가 모든 메뉴를 조회했습니다.", userId);
		ResponseWithData<DishSummaryResultDto> allDishes = dishService.getAllDishes(userId);
		return ResponseEntity.status(allDishes.status())
			.body(allDishes);


	}

}
