package com.qxcjs.demo.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class CustomExceptionHandler {
    @ExceptionHandler(value = AuthenticationException.class)
    public String exceptionHandler(Exception e) {
        log.error("global exception : {}", e);
        return e.getMessage();
    }
}
