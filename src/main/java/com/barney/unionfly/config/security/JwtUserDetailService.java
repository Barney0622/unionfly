package com.barney.unionfly.config.security;

import com.barney.unionfly.config.exception.Error401;
import com.barney.unionfly.domain.user.User;
import com.barney.unionfly.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByName(username);

        if (ObjectUtils.isEmpty(user)) {
            throw new Error401("User not found whit username: " + username);
        }

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), new ArrayList<>());
    }

}
