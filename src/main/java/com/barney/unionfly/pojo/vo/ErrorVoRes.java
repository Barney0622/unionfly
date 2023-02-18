package com.barney.unionfly.pojo.vo;

import com.barney.unionfly.enums.error_code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorVoRes {
    private String message;

    public ErrorVoRes(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }
}
