package com.maracuya.app.common.error;

import lombok.Value;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Value
public class ValidationErrorResponse implements ErrorResponse {

    HttpStatus status = BAD_REQUEST;
    String message;
    List<FieldError> errors;

    public ValidationErrorResponse(String message) {
        this(message, null);
    }

    public ValidationErrorResponse(String message, List<FieldError> errors) {
        this.message = message;
        this.errors = errors;
    }
}
