package com.example.backend.table.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TableResponse() {

	@Schema(name = "QR정보",description = "재생성된 QR정보를 담는다")
	public record QRInfo(
		String url
	){

	}

}
