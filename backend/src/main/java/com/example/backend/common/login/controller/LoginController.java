package com.example.backend.common.login.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.common.dto.CommonResponse;
import com.example.backend.common.dto.CommonResponse.ResponseWithData;
import com.example.backend.common.login.dto.LoginInfo;
import com.example.backend.common.login.dto.LoginRequestDto;
import com.example.backend.common.login.service.LoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/owner/login")
@Slf4j
@Tag(name = "사용자 인증 API",description = "점주 로그인을 수행하기위한 Controller입니다")
public class LoginController {

	private final LoginService loginService;

	@Operation(summary = "로그인 코드", description = "로그인 코드를 받아 로그인을 하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "로그인 완료"),
	})
	@PostMapping("")
	public ResponseEntity<ResponseWithData<LoginInfo>> login(@RequestBody LoginRequestDto loginRequestDto) {
		// log.warn("code = {}",code);
		LoginInfo loginInfo = loginService.login(loginRequestDto);

		ResponseWithData<LoginInfo> responseWithData = new ResponseWithData<>(HttpStatus.OK.value(),
			"로그인에 성공하였습니다",loginInfo);

		return ResponseEntity.status(responseWithData.status())
			.body(responseWithData);
	}
}
