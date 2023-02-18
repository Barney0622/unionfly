package com.barney.unionfly.enums.error_code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode401 implements ErrorCode{
    UNAUTHORIZED("Unauthorized"),
    ACCESS_DENIED("Access denied")
    ;

    private final String message;
}
