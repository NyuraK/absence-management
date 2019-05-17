package com.netcracker.vacations.exception;

public class MailServiceException extends ApiException {

    public MailServiceException(String message) {
        super("Couldn't send a message due to " + message);
    }

}
