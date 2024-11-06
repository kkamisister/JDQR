package com.example.backend.table.controller;

import static com.example.backend.common.dto.CommonResponse.*;
import static com.example.backend.table.dto.TableRequest.*;
import static com.example.backend.table.dto.TableResponse.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.table.dto.TableResponse;
import com.example.backend.table.service.TableService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/owner/table")
@RequiredArgsConstructor
@Slf4j
public class TableController {

	private final TableService tableService;

	@Operation(summary = "전체 테이블 조회", description = "전체 테이블을 조회하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "전체 테이블 조회완료"),
	})
	@GetMapping("")
	public ResponseEntity<?> getAllTables(HttpServletRequest request){

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		tableService.getAllTables(userId);
		return null;
	}


	@Operation(summary = "QR 생성", description = "QR을 재생성하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "QR 생성 완료"),
	})
	@PostMapping("/qr")
	public ResponseEntity<ResponseWithData<QRInfo>> createQR(@RequestParam("tableId") String tableId,HttpServletRequest request){

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		QRInfo qr = tableService.createQR(tableId, userId);

		ResponseWithData<QRInfo> responseWithData = new ResponseWithData<>(HttpStatus.OK.value(), "URL 생성에 성공하였습니다",qr);

		return ResponseEntity.status(responseWithData.status())
			.body(responseWithData);
	}

	@Operation(summary = "테이블 생성", description = "정보를 받아 테이블을 생성하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "테이블 생성 완료"),
	})
	@PostMapping("")
	public ResponseEntity<ResponseWithMessage> createTable(@RequestBody TableInfo tableInfo, HttpServletRequest request) {

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		tableService.createTable(tableInfo, userId);

		ResponseWithMessage responseWithMessage = new ResponseWithMessage(HttpStatus.OK.value(),
			"테이블 생성에 성공하였습니다");

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

	@Operation(summary = "테이블 삭제", description = "테이블을 삭제하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "테이블 삭제 완료"),
	})
	@DeleteMapping("")
	public ResponseEntity<ResponseWithMessage> deleteTable(@RequestParam("tableId") String tableId, HttpServletRequest request){

		log.warn("tableId : {}",tableId);

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		tableService.deleteTable(tableId,userId);

		ResponseWithMessage responseWithMessage = new ResponseWithMessage(HttpStatus.OK.value(),"테이블이 삭제되었습니다");

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}

	@Operation(summary = "테이블 수정", description = "테이블을 수정하는 api")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "테이블 수정 완료"),
	})
	@PutMapping("")
	public ResponseEntity<ResponseWithMessage> updateTable(@RequestBody TableInfo tableInfo,
		HttpServletRequest request) {

		String id = (String)request.getAttribute("userId");
		Integer userId = Integer.valueOf(id);

		tableService.updateTable(tableInfo,userId);

		ResponseWithMessage responseWithMessage =  new ResponseWithMessage(
			HttpStatus.OK.value(), "테이블 수정에 성공하였습니다"
		);

		return ResponseEntity.status(responseWithMessage.status())
			.body(responseWithMessage);
	}
}
