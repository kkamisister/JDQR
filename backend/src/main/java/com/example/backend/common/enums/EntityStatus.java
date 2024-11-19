package com.example.backend.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EntityStatus {
    ACTIVE("활성화"),
    DELETE("비활성화");

    private final String explain;
}
