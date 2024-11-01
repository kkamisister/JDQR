package com.example.backend.table.controller;

import static com.example.backend.common.dto.CommonResponse.*;
import static com.example.backend.table.dto.TableRequest.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.table.service.TableService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/table")
@RequiredArgsConstructor
public class TableController {

	private final TableService tableService;

	@Operation(summary = "테이블 생성", description = "정보를 받아 테이블을 생성하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "테이블 생성 완료"),
	})
	@PostMapping("")
	public ResponseEntity<ResponseWithData<String>> createTable(@RequestBody TableInfo tableInfo, HttpServletRequest request) {

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		ResponseWithData<String> responseWithData = tableService.createTable(tableInfo, userId);

		return ResponseEntity.status(responseWithData.status())
			.body(responseWithData);
	}

}
