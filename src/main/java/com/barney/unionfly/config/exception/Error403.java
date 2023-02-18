package com.barney.unionfly.config.exception;

import com.barney.unionfly.enums.error_code.ErrorCode403;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Error403 extends RuntimeException implements ErrorException {

    public Error403(String message) {
        super(message);
    }

    public Error403(ErrorCode403 errorCode) {
        super(errorCode.getMessage());
    }

    @Override
    public String getOutputMsg() {
        return ErrorCode403.FORBIDDEN.getMessage();
    }
}
