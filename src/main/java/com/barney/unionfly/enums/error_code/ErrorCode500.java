package com.barney.unionfly.enums.error_code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode500 implements ErrorCode{
    INTERNAL_ERROR("Internal error"),
    DATABASE_ERROR("Database error"),
    UNEXPECTED_ERROR("Unexpected error")
    ;

    private final String message;
}
