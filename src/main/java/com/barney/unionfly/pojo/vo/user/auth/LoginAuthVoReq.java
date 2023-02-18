package com.barney.unionfly.pojo.vo.user.auth;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginAuthVoReq {
    @NotBlank
    private String name;
    @NotBlank
    private String password;
}
