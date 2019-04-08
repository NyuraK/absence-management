package com.netcracker.vacations.domain.exceptions;

public class EndingBeforeDateException extends Exception {
    public final String message="EXCEPTION: Date of ending of request is incorrect (it is before beginning date)";
}
