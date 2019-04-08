package com.netcracker.vacations.exception;

public class ApiException extends RuntimeException {
    public final static String message = "Date of request is incorrect";

    public ApiException(String message){
        super(message);
    }
}
