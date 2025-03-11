package com.example.Complaints.Management.System.core.application.dto;

public class ErrorResponseDto {

    private String message;
    private String field;
    private Object Value;

    public ErrorResponseDto(String message, String field, Object value) {
        this.message = message;
        this.field = field;
        Value = value;
    }

    public ErrorResponseDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return Value;
    }

    public void setValue(Object value) {
        Value = value;
    }
}
