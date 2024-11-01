package com.example.backend.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedirectUrl {

	FRONT("http://localhost:3000/tableToken");

	private final String explain;
}
