package com.netcracker.vacations.exception;

public class EndingBeforeBeginningException extends ApiException {
    public final static String message = "EXCEPTION: Date of ending of request is incorrect (it is before beginning date)";

    public EndingBeforeBeginningException(){
        super(message);
    }
}
