package com.barney.unionfly.config.exception;

import com.barney.unionfly.enums.error_code.ErrorCode401;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Error401 extends RuntimeException implements ErrorException {

    public Error401(String message) {
        super(message);
    }

    public Error401(ErrorCode401 errorCode) {
        super(errorCode.getMessage());
    }

    @Override
    public String getOutputMsg() {
        return ErrorCode401.UNAUTHORIZED.getMessage();
    }
}
