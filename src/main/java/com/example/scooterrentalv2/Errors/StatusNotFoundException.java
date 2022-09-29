package com.example.scooterrentalv2.Errors;

public class StatusNotFoundException extends RuntimeException{
    public StatusNotFoundException() {
        super();
    }

    public StatusNotFoundException(String message) {
        super(message);
    }

    public StatusNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatusNotFoundException(Throwable cause) {
        super(cause);
    }

    protected StatusNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
