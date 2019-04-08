package com.netcracker.vacations.domain.exceptions;

public class BeginningAfterEndingException extends Exception {
    public final String message="EXCEPTION: Date of beginning of request is incorrect (it is after ending date)";
}
