package com.barney.unionfly.config.filter;

import com.barney.unionfly.config.security.JwtService;
import com.barney.unionfly.config.security.JwtUserDetailService;
import com.barney.unionfly.enums.error_code.ErrorCode401;
import com.barney.unionfly.pojo.vo.ErrorVoRes;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtUserDetailService jwtUserDetailService;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    @Value("${server.security.permitUrl}")
    private String[] permitUrl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isPermitUrl(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("request URL: {}", request.getRequestURL());

        String authorizationHeader = getAuthorizationHeader(request);

        if (StringUtils.isBlank(authorizationHeader) || BooleanUtils.isFalse(authorizationHeader.startsWith(BEARER_PREFIX))) {
            log.warn("JWT is empty or not start with Bearer");
            throwError401(response);
            return;
        }

        String name;
        String jwtToken = authorizationHeader.substring(BEARER_PREFIX.length());

        Jws<Claims> jws;
        try {
            jws = jwtService.parseJwt(jwtToken);
        } catch (IllegalArgumentException | SignatureException | ExpiredJwtException | UnsupportedJwtException |
                 MalformedJwtException e) {
            log.error("JWT exception message: {}", e.getMessage(), e);
            throwError401(response);
            return;
        }

        name = jws.getBody().getSubject();
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(name);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("filter is working");

        filterChain.doFilter(request, response);
    }

    private String getAuthorizationHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return StringUtils.isBlank(authorizationHeader) ? null : authorizationHeader;
    }

    private boolean isPermitUrl(String servletPath) {
        return Arrays.stream(permitUrl).anyMatch(pattern -> new AntPathMatcher().match(pattern, servletPath));
    }

    private void throwError401(HttpServletResponse response) throws IOException {
        ErrorVoRes errorVoRes = new ErrorVoRes(ErrorCode401.UNAUTHORIZED);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        PrintWriter writer = response.getWriter();
        writer.print(objectMapper.writeValueAsString(errorVoRes));
        writer.flush();
    }
}
