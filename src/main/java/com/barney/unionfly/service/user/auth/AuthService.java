package com.barney.unionfly.service.user.auth;

import com.barney.unionfly.config.security.JwtUtils;
import com.barney.unionfly.domain.user.User;
import com.barney.unionfly.pojo.dto.user.UserDto;
import com.barney.unionfly.pojo.vo.user.auth.LoginAuthVoReq;
import com.barney.unionfly.pojo.vo.user.auth.LoginAuthVoRes;
import com.barney.unionfly.pojo.vo.user.auth.RegisterVoReq;
import com.barney.unionfly.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<LoginAuthVoRes> login(LoginAuthVoReq req) {
        User user = userService.findByName(req.getName());
        if (ObjectUtils.isEmpty(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(buildFailLoginAuthVoRes());
        }

        if (BooleanUtils.isFalse(bCryptPasswordEncoder.matches(req.getPassword(), user.getPassword()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(buildFailLoginAuthVoRes());
        }

        String token = JwtUtils.createJwt(user.getName());
        return ResponseEntity.ok(LoginAuthVoRes.builder().token(token).build());
    }

    public ResponseEntity<String> register(RegisterVoReq req) {

        if (ObjectUtils.isNotEmpty(userService.findByName(req.getName()))) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("UserName already exists");
        }

        UserDto userDto = UserDto.builder()
                .name(req.getName())
                .password(bCryptPasswordEncoder.encode(req.getPassword()))
                .build();

        userService.createUser(userDto);

        return ResponseEntity.ok("Register success!");
    }

    private LoginAuthVoRes buildFailLoginAuthVoRes() {
        return LoginAuthVoRes.builder()
                .token("login failure!")
                .build();
    }
}
