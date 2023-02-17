package com.barney.unionfly.pojo.vo.user.auth;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginAuthVoReq {
    private String name;
    private String password;
}
