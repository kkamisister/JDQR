package com.example.backend.common.exception;

import lombok.Getter;

@Getter
public class JDQRException extends RuntimeException{
	private final ErrorCode errorCode;

	public JDQRException(ErrorCode errorCode){
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}


}
