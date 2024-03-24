package com.hello.devtest.intercepters;

import com.hello.devtest.exceprtions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = {CustomException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String deniedPermissionException(CustomException ex) {
        return ex.getMessage();
    }
}
