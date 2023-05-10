package com.maracuya.app.common.error;

import org.springframework.http.HttpStatus;

public interface ErrorResponse {

    HttpStatus getStatus();

    String getMessage();
}
