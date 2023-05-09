package com.maracuya.app.common.error;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ValidationErrorResponse(
    HttpStatus status,
    String message,
    List<FieldError> errors
) {
}
