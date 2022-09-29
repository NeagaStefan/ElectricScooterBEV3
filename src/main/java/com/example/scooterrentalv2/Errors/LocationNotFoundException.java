package com.example.scooterrentalv2.Errors;

public class LocationNotFoundException extends  RuntimeException{
    public LocationNotFoundException() {
        super();
    }

    public LocationNotFoundException(String message) {
        super(message);
    }

    public LocationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocationNotFoundException(Throwable cause) {
        super(cause);
    }

    protected LocationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
