package com.example.backend.common.enums;

import org.springframework.stereotype.Repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UseStatus {

	AVAILABLE("이용가능"),
	OCCUPIED("사용중");

	private final String explain;
}
