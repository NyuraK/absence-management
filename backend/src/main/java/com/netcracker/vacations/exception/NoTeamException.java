package com.netcracker.vacations.exception;

public class NoTeamException extends RuntimeException {
    private String msg;

    public NoTeamException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
