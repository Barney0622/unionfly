package com.barney.unionfly;

import com.barney.unionfly.domain.user.User;
import com.barney.unionfly.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void getUser(){
        String testName = "test";

        User user = userRepository.findByName(testName);

        System.out.println(user);
    }

    @Test
    void getAllUser(){
        userRepository.findAll().forEach(System.out::println);
    }
}
