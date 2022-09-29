package com.example.scooterrentalv2.Errors;

public class ScooterNotFoundException extends RuntimeException{
    public ScooterNotFoundException() {
        super();
    }

    public ScooterNotFoundException(String message) {
        super(message);
    }

    public ScooterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScooterNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ScooterNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
