package com.example.backend.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserCookie {

	USER_COOKIE("JDQR-order-user-id");

	private final String explain;
}
