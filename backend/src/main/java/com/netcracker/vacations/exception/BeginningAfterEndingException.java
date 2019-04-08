package com.netcracker.vacations.exception;

public class BeginningAfterEndingException extends ApiException {
    public final static String message = "EXCEPTION: Date of beginning of request is incorrect (it is after ending date)";

    public BeginningAfterEndingException(){
        super(message);
    }
}
