package com.netcracker.vacations.exception;

public class ApiException extends RuntimeException {
    protected String msg;

    public ApiException(String message){
        super(message);
        this.msg = message;
    }

    public String getMsg() {
        return msg;
    }
}
