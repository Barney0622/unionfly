package com.barney.unionfly.config.exception;

import com.barney.unionfly.enums.error_code.ErrorCode400;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Error400 extends RuntimeException implements ErrorException {

    public Error400(String message) {
        super(message);
    }

    public Error400(ErrorCode400 errorCode) {
        super(errorCode.getMessage());
    }

    @Override
    public String getOutputMsg() {
        return ErrorCode400.BAD_REQUEST.getMessage();
    }
}
