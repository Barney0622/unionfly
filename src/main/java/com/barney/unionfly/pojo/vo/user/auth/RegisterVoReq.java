package com.barney.unionfly.pojo.vo.user.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVoReq {
    private String name;
    private String password;
}
