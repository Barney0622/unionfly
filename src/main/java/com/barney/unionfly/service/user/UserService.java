package com.barney.unionfly.service.user;

import com.barney.unionfly.domain.user.User;
import com.barney.unionfly.pojo.dto.user.UserDto;
import com.barney.unionfly.pojo.vo.user.UserVoRes;
import com.barney.unionfly.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByName(String name) {
        return userRepository.findByName(name).orElse(null);
    }

    @Transactional
    public void createUser(UserDto dto) {
        User user = User.builder()
                .name(dto.getName())
                .password(dto.getPassword())
                .build();

        userRepository.save(user);
    }

    public UserVoRes getTestData() {
        User user = findByName("test");
        return UserVoRes.builder()
                .name(user.getName())
                .text("測試api")
                .build();
    }
}
