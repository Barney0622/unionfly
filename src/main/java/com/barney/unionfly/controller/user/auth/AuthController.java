package com.barney.unionfly.controller.user.auth;

import com.barney.unionfly.pojo.vo.user.auth.LoginAuthVoReq;
import com.barney.unionfly.pojo.vo.user.auth.LoginAuthVoRes;
import com.barney.unionfly.pojo.vo.user.auth.RegisterVoReq;
import com.barney.unionfly.service.user.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginAuthVoRes> login(@RequestBody @Valid LoginAuthVoReq req) {
        return authService.login(req);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterVoReq req) {
        return authService.register(req);
    }
}
