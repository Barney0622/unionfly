package com.barney.unionfly.config.exception;

import com.barney.unionfly.enums.error_code.ErrorCode401;
import com.barney.unionfly.enums.error_code.ErrorCode500;
import com.barney.unionfly.pojo.vo.ErrorVoRes;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class ThrowableHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorVoRes httpRequestMethodNotSupportedException(Throwable e) {
        log.error("Http request method not supported message:{}", e.getMessage(), e);

        return new ErrorVoRes(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorVoRes httpMessageNotReadableException(Throwable e) {
        log.error("Http message not readable message:{}", e.getMessage(), e);

        return new ErrorVoRes(e.getMessage());
    }

    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorVoRes authException(Throwable e) {
        log.error("Auth exception message:{}", e.getMessage(), e);

        return new ErrorVoRes(ErrorCode401.UNAUTHORIZED);
    }

    @ExceptionHandler({JwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorVoRes handleJwtExceptions(HttpServletRequest request, Exception e) {
        log.error("JWT exception caught, URI: {}", request.getRequestURI(), e);
        return new ErrorVoRes(ErrorCode401.UNAUTHORIZED);
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorVoRes databaseException(Throwable e) {
        log.error("Database exception message:{}", e.getMessage(), e);

        return new ErrorVoRes(ErrorCode500.DATABASE_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorVoRes accessDeniedException(Throwable e) {
        log.error("Access denied exception message:{}", e.getMessage(), e);

        return new ErrorVoRes(ErrorCode401.ACCESS_DENIED);
    }

    @ExceptionHandler(Error400.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorVoRes error400(Error400 e) {
        String message = ObjectUtils.isNotEmpty(e.getMessage()) ? e.getMessage() : e.getOutputMsg();
        log.error("Error400 message:{}", message, e);

        return new ErrorVoRes(message);
    }

    @ExceptionHandler(Error401.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorVoRes error401(Error401 e) {
        String message = ObjectUtils.isNotEmpty(e.getMessage()) ? e.getMessage() : e.getOutputMsg();
        log.error("Error401 message:{}", message, e);

        return new ErrorVoRes(message);
    }

    @ExceptionHandler(Error403.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorVoRes error403(Error403 e) {
        String message = ObjectUtils.isNotEmpty(e.getMessage()) ? e.getMessage() : e.getOutputMsg();
        log.error("Error403 message:{}", message, e);

        return new ErrorVoRes(message);
    }

    @ExceptionHandler(Error500.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorVoRes error500(Error500 e) {
        String message = ObjectUtils.isNotEmpty(e.getMessage()) ? e.getMessage() : e.getOutputMsg();
        log.error("Error500 message:{}", message, e);

        return new ErrorVoRes(message);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorVoRes handleError(HttpServletRequest request, Throwable e) {
        String apiPath = getApiPath(request);
        log.error("Global exception, Api path:{}, message:{}", apiPath, e.getMessage(), e);

        return new ErrorVoRes(ErrorCode500.UNEXPECTED_ERROR);
    }

    private String getApiPath(HttpServletRequest request) {
        return request.getRequestURI().substring(request.getContextPath().length());
    }

}
