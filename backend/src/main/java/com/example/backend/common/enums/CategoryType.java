package com.example.backend.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryType {
    MAJOR("검색 조건으로 사용"),
    MINOR("세부 태그");

    private final String explain;
}
