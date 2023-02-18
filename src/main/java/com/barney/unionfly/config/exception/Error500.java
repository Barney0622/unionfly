package com.barney.unionfly.config.exception;

import com.barney.unionfly.enums.error_code.ErrorCode500;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Error500 extends RuntimeException implements ErrorException {

    public Error500(String message) {
        super(message);
    }

    public Error500(ErrorCode500 errorCode) {
        super(errorCode.getMessage());
    }

    @Override
    public String getOutputMsg() {
        return ErrorCode500.INTERNAL_ERROR.getMessage();
    }
}
