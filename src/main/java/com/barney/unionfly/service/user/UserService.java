package com.barney.unionfly.service.user;

import com.barney.unionfly.config.security.JwtService;
import com.barney.unionfly.domain.user.User;
import com.barney.unionfly.pojo.dto.user.UserDto;
import com.barney.unionfly.pojo.vo.user.UserVoRes;
import com.barney.unionfly.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

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

    public UserVoRes getMyselfName(String token) {
        String name = jwtService.parseJwt(token).getBody().getSubject();

        return UserVoRes.builder()
                .name(name)
                .build();
    }

    public UserVoRes getSecurityContextHolderUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return UserVoRes.builder()
                .name(userDetails.getUsername())
                .build();
    }
}
