package com.example.Complaints.Management.System.shared.Utils;


public class CustomValidationException extends RuntimeException{

    private String fieldName;
    private Object value;

    public CustomValidationException() {
    }

    public CustomValidationException(String message, String fieldName, Object value) {
        super(message);
        this.fieldName = fieldName;
        this.value = value;
    }

    public CustomValidationException(String message) {
        super(message);
    }

    public CustomValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomValidationException(Throwable cause) {
        super(cause);
    }

    public CustomValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
