package com.example.scooterrentalv2.Errors;

public class RentalAlreadyStartedException extends RuntimeException{
    public RentalAlreadyStartedException() {
        super();
    }

    public RentalAlreadyStartedException(String message) {
        super(message);
    }

    public RentalAlreadyStartedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RentalAlreadyStartedException(Throwable cause) {
        super(cause);
    }

    protected RentalAlreadyStartedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
