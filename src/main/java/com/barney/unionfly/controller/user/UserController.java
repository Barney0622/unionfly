package com.barney.unionfly.controller.user;

import com.barney.unionfly.pojo.vo.user.UserVoRes;
import com.barney.unionfly.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/test")
    public UserVoRes getTestData(){
        return userService.getTestData();
    }
}
