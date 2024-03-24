package com.hello.devtest.exceprtions;

public class CustomException extends RuntimeException {
    public CustomException(String s, Throwable cause) {
        super(s, cause);
    }
}
