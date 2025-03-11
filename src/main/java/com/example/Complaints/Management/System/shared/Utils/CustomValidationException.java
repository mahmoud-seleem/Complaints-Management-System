package com.example.Complaints.Management.System.shared.Utils;


import com.example.Complaints.Management.System.core.application.dto.ErrorResponseDto;

public class CustomValidationException extends RuntimeException{

    private ErrorResponseDto response;
    public CustomValidationException() {
    }

    public CustomValidationException(String message, String fieldName, Object value) {
        super(message);
        this.response = new ErrorResponseDto(message,fieldName,value);
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

    public ErrorResponseDto getResponse() {
        return response;
    }

    public void setResponse(ErrorResponseDto response) {
        this.response = response;
    }
}
