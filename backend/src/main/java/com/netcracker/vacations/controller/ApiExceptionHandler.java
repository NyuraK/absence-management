package com.netcracker.vacations.controller;

import com.netcracker.vacations.exception.NoTeamException;
import com.netcracker.vacations.exception.TooManyDaysException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = TooManyDaysException.class)
    public ResponseEntity<?> handleException(TooManyDaysException exception) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMsg());
    }

    @ResponseBody
    @ExceptionHandler(value = NoTeamException.class)
    public ResponseEntity<?> handleException(NoTeamException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMsg());
    }
}
