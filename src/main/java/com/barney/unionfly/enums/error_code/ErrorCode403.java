package com.barney.unionfly.enums.error_code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode403 implements ErrorCode{
    FORBIDDEN("Forbidden")
    ;

    private final String message;
}
