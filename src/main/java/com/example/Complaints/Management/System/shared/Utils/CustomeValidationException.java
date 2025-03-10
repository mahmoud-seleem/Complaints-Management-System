package com.example.Complaints.Management.System.shared.Utils;


public class CustomeValidationException extends RuntimeException{

    private String fieldName;
    private Object value;

    public CustomeValidationException() {
    }

    
    public CustomeValidationException(String message) {
        super(message);
    }

    public CustomeValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomeValidationException(Throwable cause) {
        super(cause);
    }

    public CustomeValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
