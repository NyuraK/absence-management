package com.netcracker.vacations.exception;

import org.springframework.http.HttpStatus;

public class MyAuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final HttpStatus httpStatus;

    public MyAuthenticationException(RuntimeException e, String message, HttpStatus httpStatus) {
        super(message, e);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
