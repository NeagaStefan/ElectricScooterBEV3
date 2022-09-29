package com.example.scooterrentalv2.Errors;

public class StartNotFoundException extends  RuntimeException{
    public StartNotFoundException() {
        super();
    }

    public StartNotFoundException(String message) {
        super(message);
    }

    public StartNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StartNotFoundException(Throwable cause) {
        super(cause);
    }

    protected StartNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
