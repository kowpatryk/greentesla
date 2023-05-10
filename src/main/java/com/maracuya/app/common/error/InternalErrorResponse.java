package com.maracuya.app.common.error;

import lombok.Value;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Value
public class InternalErrorResponse implements ErrorResponse {

    HttpStatus status = INTERNAL_SERVER_ERROR;
    String message;
}
