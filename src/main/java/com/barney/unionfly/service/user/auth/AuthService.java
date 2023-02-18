package com.barney.unionfly.service.user.auth;

import com.barney.unionfly.config.exception.Error400;
import com.barney.unionfly.config.exception.Error401;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginAuthVoRes login(LoginAuthVoReq req) {
        User user = userService.findByName(req.getName());

        if (ObjectUtils.isEmpty(user) || BooleanUtils.isFalse(bCryptPasswordEncoder.matches(req.getPassword(), user.getPassword()))) {
            throw new Error401("name or password incorrect");
        }

        String token = JwtUtils.createJwt(user.getName());
        return LoginAuthVoRes.builder().token(token).build();
    }

    public void register(RegisterVoReq req) {

        if (ObjectUtils.isNotEmpty(userService.findByName(req.getName()))) {
            throw new Error400("duplicate name");
        }

        UserDto userDto = UserDto.builder()
                .name(req.getName())
                .password(bCryptPasswordEncoder.encode(req.getPassword()))
                .build();

        userService.createUser(userDto);
    }
}
