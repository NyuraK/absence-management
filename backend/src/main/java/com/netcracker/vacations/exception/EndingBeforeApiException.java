package com.netcracker.vacations.exception;

public class EndingBeforeApiException extends ApiException {
    public final String message = "EXCEPTION: Date of ending of request is incorrect (it is before beginning date)";
}
