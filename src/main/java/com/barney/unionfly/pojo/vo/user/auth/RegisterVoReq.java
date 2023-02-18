package com.barney.unionfly.pojo.vo.user.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVoReq {
    @NotBlank
    private String name;
    @NotBlank
    private String password;
}
