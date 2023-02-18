package com.barney.unionfly.enums.error_code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode400 implements ErrorCode{
    BAD_REQUEST("Bad Request"),
    NOT_FOUND_RESOURCE("Not found resource"),
    RESOURCE_DUPLICATE("Resource duplicate"),
    PARAMETER_INVALID("Parameter invalid")
    ;

    private final String message;
}
