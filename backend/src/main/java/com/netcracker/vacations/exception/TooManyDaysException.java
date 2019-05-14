package com.netcracker.vacations.exception;

public class TooManyDaysException extends ApiException {
    private String msg;

    public TooManyDaysException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
